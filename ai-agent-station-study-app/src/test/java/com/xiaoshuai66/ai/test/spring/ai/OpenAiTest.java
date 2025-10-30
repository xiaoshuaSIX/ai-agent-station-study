package com.xiaoshuai66.ai.test.spring.ai;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenAiTest {

    private final String model = "qwen3-max";
    private final String vlModel = "qwen3-vl-plus";

    @Value("classpath:data/dog.png")
    private Resource imageResource;

    @Value("classpath:data/file.txt")
    private Resource textResource;

    @Value("classpath:data/article-prompt-words.txt")
    private Resource articlePromptWordsResource;

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Autowired
    private PgVectorStore pgVectorStore;

    private final TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();

    @Test
    public void test_call() {
        ChatResponse response = openAiChatModel.call(new Prompt(
                "1+1",
                OpenAiChatOptions.builder()
                        .model(model)
                        .build()));
        log.info("测试结果(call):{}", JSON.toJSONString(response));
    }

    @Test
    public void test_call_images() {
        UserMessage userMessage = UserMessage.builder()
                .text("请描述这张图片的主要内容，并说明图中物品的可能用途。")
                .media(Media.builder()
                        .mimeType(MimeType.valueOf(MimeTypeUtils.IMAGE_PNG_VALUE))
                        .data(imageResource)
                        .build())
                .build();

        ChatResponse response = openAiChatModel.call(new Prompt(
                userMessage,
                OpenAiChatOptions.builder()
                        .model(vlModel)
                        .build()));

        log.info("测试结果(images):{}", JSON.toJSONString(response));
    }

    @Test
    public void test_stream() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flux<ChatResponse> stream = openAiChatModel.stream(new Prompt(
                "1+1",
                OpenAiChatOptions.builder()
                        .model(model)
                        .build()));

        stream.subscribe(
                chatResponse -> {
                    AssistantMessage output = chatResponse.getResult().getOutput();
                    log.info("测试结果(stream):{}", JSON.toJSONString(output));
                },
                Throwable::printStackTrace,
                () -> {
                    // 将计数-1,减到0时进行释放
                    countDownLatch.countDown();
                    log.info("测试结果(stream):done!");
                }
        );

        countDownLatch.await();
    }

    @Test
    public void upload(){
        // textResource、articlePromptWordsResource
        // spring ai 提供的文档读取器，基于Apache Tika 自动识别并解析资源文件为统一的Document文件
        // 便于后续做文本分块，向量化入库或作为Prompt上下文使用
//        TikaDocumentReader reader = new TikaDocumentReader(articlePromptWordsResource);
        TikaDocumentReader reader = new TikaDocumentReader(textResource);

        List<Document> documents = reader.get();
        // TokenTextSplitter spring ai 的文本分割器，按“token”长度将文本切成多块
        List<Document> documentSplitterList = tokenTextSplitter.apply(documents);

        // 给每个文档分片添加自定义元数据标签
        // 给所有分片打上“知识库/主题”标识，便于向量库入库后按条件筛选、命名空间隔离、检索时过滤
//        documentSplitterList.forEach(doc -> doc.getMetadata().put("knowledge", "article-prompt-words"));
        documentSplitterList.forEach(doc -> doc.getMetadata().put("knowledge", "知识库名称-v4"));

        pgVectorStore.accept(documentSplitterList);

        log.info("上传完成");
    }

    @Test
    public void chat(){
        String message = "王大瓜今年几岁";

        // 系统提示词模板，包含占位符{documents}，用于把检索到的文档内容拼接进去，指导模型基于文档作答，并要求用中文回答
        String SYSTEM_PROMPT = """
                Use the information from the DOCUMENTS section to provide accurate answers but act as if you knew this information innately.
                If unsure, simply state that you don't know.
                Another thing you need to note is that your reply must be in Chinese!
                DOCUMENTS:
                    {documents}
                """;

        SearchRequest request = SearchRequest.builder()
                // query：用用户问题生成检索向量。
                .query(message)
                // topK(5)：返回最相似的 5 条文档片段。
                .topK(5)
                // filterExpression：基于元数据过滤，仅从标记为 knowledge == '知识库名称-v4' 的命名空间/主题中检索，避免“串库”。
                .filterExpression("knowledge == '知识库名称-v4'")
                .build();

        // 调用 pgvector 向量库进行相似度检索，返回候选文档片段列表
        List<Document> documents = pgVectorStore.similaritySearch(request);

        // 将检索到的 Document 列表提取出纯文本内容并拼接为一个字符串，作为 RAG 的上下文注入到提示词中；若无结果则为空字符串。
        String documentCollectors = null == documents ? "" : documents.stream().map(Document::getText).collect(Collectors.joining());

        Message ragMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage(Map.of("documents", documentCollectors));

        // 构造对话消息列表
        // 第一条是用户消息(User角色)，包含原始问题
        // 第二条是系统消息(System角色)，包含注入的文档上下文与作答指令
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new UserMessage(message));
        messages.add(ragMessage);

        ChatResponse chatResponse = openAiChatModel.call(new Prompt(
                messages,
                OpenAiChatOptions.builder()
                        .model(model)
                        .build()));

        log.info("测试结果:{}", JSON.toJSONString(chatResponse));
    }

}
