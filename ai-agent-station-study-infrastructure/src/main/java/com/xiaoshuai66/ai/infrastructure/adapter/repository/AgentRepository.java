package com.xiaoshuai66.ai.infrastructure.adapter.repository;

import com.alibaba.fastjson2.JSON;
import com.xiaoshuai66.ai.domain.agent.adapter.repository.IAgentRepository;
import com.xiaoshuai66.ai.domain.agent.model.valobj.*;
import com.xiaoshuai66.ai.infrastructure.dao.*;
import com.xiaoshuai66.ai.infrastructure.dao.po.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.xiaoshuai66.ai.domain.agent.model.valobj.AiAgentEnumVO.AI_CLIENT;
import static com.xiaoshuai66.ai.domain.agent.model.valobj.AiAgentEnumVO.AI_CLIENT_MODEL;

/**
 * AiAgent 仓储服务
 *
 * @Author 赵帅
 * @Create 2025/10/30 22:10
 */
@Slf4j
@Repository
public class AgentRepository implements IAgentRepository {

    @Resource
    private IAiAgentDao aiAgentDao;

    @Resource
    private IAiAgentFlowConfigDao aiAgentFlowConfigDao;

    @Resource
    private IAiAgentTaskScheduleDao aiAgentTaskScheduleDao;

    @Resource
    private IAiClientDao aiClientDao;

    @Resource
    private IAiClientAdvisorDao aiClientAdvisorDao;

    @Resource
    private IAiClientApiDao aiClientApiDao;

    @Resource
    private IAiClientConfigDao aiClientConfigDao;

    @Resource
    private IAiClientModelDao aiClientModelDao;

    @Resource
    private IAiClientRagOrderDao aiClientRagOrderDao;

    @Resource
    private IAiClientSystemPromptDao aiClientSystemPromptDao;

    @Resource
    private IAiClientToolMcpDao aiClientToolMcpDao;

    @Override
    public List<AiClientApiVO> queryAiClientApiVOListByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientApiVO> result = new ArrayList<>();

        for (String clientId : clientIdList) {
            // 1.通过clientId查询关联的modelId
            List<AiClientConfig> configs = aiClientConfigDao.selectBySourceIdAndType(clientId, AI_CLIENT.getCode());

            for (AiClientConfig config : configs) {

                if (AI_CLIENT_MODEL.getCode().equals(config.getTargetType()) && config.getStatus() == 1) {
                    String modelId = config.getTargetId();

                    // 2.通过modelId查询模型配置，获取apiId
                    AiClientModel model = aiClientModelDao.selectByModelId(modelId);
                    if (model != null && model.getStatus() == 1) {
                        String apiId = model.getApiId();

                        // 3.通过apiId查询API配置信息
                        AiClientApi apiConfig = aiClientApiDao.selectByApiId(apiId);
                        if (apiConfig != null && apiConfig.getStatus() == 1) {
                            // 4.转换为VO对象
                            AiClientApiVO apiVO = AiClientApiVO.builder()
                                    .apiId(apiConfig.getApiId())
                                    .apiKey(apiConfig.getApiKey())
                                    .baseUrl(apiConfig.getBaseUrl())
                                    .completionsPath(apiConfig.getCompletionsPath())
                                    .embeddingsPath(apiConfig.getEmbeddingsPath())
                                    .build();

                            // 避免重复添加相同的API配置
                            if (result.stream().noneMatch(vo -> vo.getApiId().equals(apiVO.getApiId()))) {
                                result.add(apiVO);
                            }
                        }
                    }
                }

            }

        }

        return result;
    }

    @Override
    public List<AiClientModelVO> AiClientModelVOByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        ArrayList<AiClientModelVO> result = new ArrayList<>();

        for (String clientId : clientIdList) {
            // 1.通过clientId查询关联的modelId
            List<AiClientConfig> configs = aiClientConfigDao.selectBySourceIdAndType(clientId, AI_CLIENT.getCode());

            for (AiClientConfig config : configs) {
                if (AI_CLIENT_MODEL.getCode().equals(config.getTargetType()) && config.getStatus() == 1) {
                    String modelId = config.getTargetId();

                    // 2.通过modelId查询模型配置
                    AiClientModel model = aiClientModelDao.selectByModelId(modelId);
                    if (model != null && model.getStatus() == 1) {
                        // 3.转换为VO对象
                        AiClientModelVO modelVO = AiClientModelVO.builder()
                                .modelId(model.getModelId())
                                .apiId(model.getApiId())
                                .modelName(model.getModelName())
                                .modelType(model.getModelType())
                                .build();

                        // 避免重复添加相同的模型配置
                        if (result.stream().noneMatch(vo -> vo.getModelId().equals(modelVO.getModelId()))) {
                            result.add(modelVO);
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<AiClientToolMcpVO> AiClientToolMcpVOByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        ArrayList<AiClientToolMcpVO> result = new ArrayList<>();
        HashSet<String> processedMcpIds = new HashSet<>();

        for (String clientId : clientIdList) {
            // 1.通过clientId查询关联的tool_mcp配置
            List<AiClientConfig> configs = aiClientConfigDao.selectBySourceIdAndType(clientId, AI_CLIENT.getCode());

            for (AiClientConfig config : configs) {
                if ("tool_mcp".equals(config.getTargetType()) && config.getStatus() == 1) {
                    String mcpId = config.getTargetId();

                    // 避免重复处理相同的mcpId
                    if (processedMcpIds.contains(mcpId)) {
                        continue;
                    }
                    processedMcpIds.add(mcpId);

                    // 2.通过mcpId查询ai_client_tool_mcp表现获取MCP工程配置
                    AiClientToolMcp toolMcp = aiClientToolMcpDao.queryByMcpId(mcpId);
                    if (toolMcp != null && toolMcp.getStatus() == 1) {
                        // 3.转换为VO对象
                        AiClientToolMcpVO mcpVO = AiClientToolMcpVO.builder()
                                .mcpId(toolMcp.getMcpId())
                                .mcpName(toolMcp.getMcpName())
                                .transportType(toolMcp.getTransportType())
                                .transportConfig(toolMcp.getTransportConfig())
                                .requestTimeout(toolMcp.getRequestTimeout())
                                .build();

                        result.add(mcpVO);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<AiClientSystemPromptVO> AiClientSystemPromptVOByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientSystemPromptVO> result = new ArrayList<>();
        Set<String> processedPromptIds = new HashSet<>();

        for (String clientId : clientIdList) {
             // 1.通过clientId查询关联的prompt配置
            List<AiClientConfig> configs = aiClientConfigDao.selectBySourceIdAndType(clientId, AI_CLIENT.getCode());

            for (AiClientConfig config : configs) {
                if ("prompt".equals(config.getTargetType()) && config.getStatus() == 1) {
                    String promptId = config.getTargetId();

                    // 避免重复处理相同的promptId
                    if (processedPromptIds.contains(promptId)) {
                        continue;
                    }
                    processedPromptIds.add(promptId);

                    // 2.通过promptId查询ai_client_system_prompt表获取系统提示词配置
                    AiClientSystemPrompt systemPrompt = aiClientSystemPromptDao.selectByPromptId(promptId);
                    if (systemPrompt != null && systemPrompt.getStatus() == 1) {
                        // 3.转换为VO对象
                        AiClientSystemPromptVO promptVO = AiClientSystemPromptVO.builder()
                                .promptId(systemPrompt.getPromptId())
                                .promptName(systemPrompt.getPromptName())
                                .promptContent(systemPrompt.getPromptContent())
                                .description(systemPrompt.getDescription())
                                .build();

                        result.add(promptVO);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<AiClientAdvisorVO> AiClientAdvisorVOByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientAdvisorVO> result = new ArrayList<>();
        Set<String> processedAdvisorIds = new HashSet<>();

        for (String clientId : clientIdList) {
            // 1.查询客户端相关的advisor配置
            List<AiClientConfig> configs = aiClientConfigDao.selectBySourceIdAndType(clientId, AI_CLIENT.getCode());

            for (AiClientConfig config : configs) {
                if (config.getStatus() != 1 || !"advisor".equals(config.getTargetType())) {
                    continue;
                }

                String advisorId = config.getTargetId();
                if (processedAdvisorIds.contains(advisorId)) {
                    continue;
                }
                processedAdvisorIds.add(advisorId);

                // 2.查询advisor详细信息
                AiClientAdvisor aiClientAdvisor = aiClientAdvisorDao.selectByAdvisorId(advisorId);
                if (aiClientAdvisor == null || aiClientAdvisor.getStatus() != 1) {
                    continue;
                }

                // 3.解析extParam中的配置
                AiClientAdvisorVO.ChatMemory chatMemory = null;
                AiClientAdvisorVO.RagAnswer ragAnswer = null;

                String extParam = aiClientAdvisor.getExtParam();
                if(extParam != null && !extParam.trim().isEmpty()) {
                    try {
                        if ("ChatMemory".equals(aiClientAdvisor.getAdvisorType())) {
                            // 解析chatMemory
                            chatMemory = JSON.parseObject(extParam, AiClientAdvisorVO.ChatMemory.class);
                        } else if ("RagAnswer".equals(aiClientAdvisor.getAdvisorType())) {
                            ragAnswer = JSON.parseObject(extParam, AiClientAdvisorVO.RagAnswer.class);
                        }
                    } catch (Exception e) {
                        // 解析失败时忽略，使用默认值null
                    }
                }

                // 4.够兼并AiClientAdvisorVO对象
                AiClientAdvisorVO advisorVO = AiClientAdvisorVO.builder()
                        .advisorId(aiClientAdvisor.getAdvisorId())
                        .advisorName(aiClientAdvisor.getAdvisorName())
                        .advisorType(aiClientAdvisor.getAdvisorType())
                        .orderNum(aiClientAdvisor.getOrderNum())
                        .chatMemory(chatMemory)
                        .ragAnswer(ragAnswer)
                        .build();

                result.add(advisorVO);
            }
        }

        return result;
    }

    @Override
    public List<AiClientVO> AiClientVOByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientVO> result = new ArrayList<>();
        Set<String> processedClientIds = new HashSet<>();

        for (String clientId : clientIdList) {
            if (processedClientIds.contains(clientId)) {
                continue;
            }
            processedClientIds.add(clientId);

            // 1.查询客户端基本信息
            AiClient aiClient = aiClientDao.selectByClientId(clientId);
            if (aiClient == null || aiClient.getStatus() != 1) {
                continue;
            }

            // 2.查询客户端相关配置
            List<AiClientConfig> configs = aiClientConfigDao.selectBySourceIdAndType(clientId, AI_CLIENT.getCode());

            String modelId = null;
            List<String> promptIdList = new ArrayList<>();
            List<String> mcpIdList = new ArrayList<>();
            List<String> advisorIdList = new ArrayList<>();

            for (AiClientConfig config : configs) {
                if (config.getStatus() != 1) {
                    continue;
                }

                switch (config.getTargetType()) {
                    case "model":
                        modelId = config.getTargetId();
                        break;
                    case "prompt":
                        promptIdList.add(config.getTargetId());
                        break;
                    case "tool_mcp":
                        mcpIdList.add(config.getTargetId());
                        break;
                    case "advisor":
                        advisorIdList.add(config.getTargetId());
                        break;
                }
            }

            // 3.构建AiClientVO对象
            AiClientVO aiClientVO = AiClientVO.builder()
                    .clientId(aiClient.getClientId())
                    .clientName(aiClient.getClientName())
                    .description(aiClient.getDescription())
                    .modelId(modelId)
                    .promoptIdList(promptIdList)
                    .mcpIdList(mcpIdList)
                    .advisorIdList(advisorIdList)
                    .build();

            result.add(aiClientVO);
        }

        return result;
    }

    @Override
    public List<AiClientApiVO> queryAiClientApiVOListByModelIds(List<String> modelIdList) {
        if (modelIdList == null || modelIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientApiVO> result = new ArrayList<>();

        for (String modelId : modelIdList) {
            // 1.通过modelId查询配置模型，获取apiId
            AiClientModel model = aiClientModelDao.selectByModelId(modelId);
            if (model != null && model.getStatus() == 1) {
                String apiId = model.getApiId();

                // 2.通过apiId查询API配置信息
                AiClientApi apiConfig = aiClientApiDao.selectByApiId(apiId);
                if (apiConfig != null && apiConfig.getStatus() == 1) {
                    // 3.转换为VO对象
                    AiClientApiVO apiVO = AiClientApiVO.builder()
                            .apiId(apiConfig.getApiId())
                            .baseUrl(apiConfig.getBaseUrl())
                            .apiKey(apiConfig.getApiKey())
                            .completionsPath(apiConfig.getCompletionsPath())
                            .embeddingsPath(apiConfig.getEmbeddingsPath())
                            .build();

                    // 避免重复添加相同的API配置
                    if (result.stream().noneMatch(vo -> vo.getApiId().equals(apiVO.getApiId()))) {
                        result.add(apiVO);
                    }
                }

            }


        }
        return result;
    }

    @Override
    public List<AiClientModelVO> AiClientModelVOByModelIds(List<String> modelIdList) {
        if (modelIdList == null || modelIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientModelVO> result = new ArrayList<>();

        for (String modelId : modelIdList) {
            // 通过modelId查询模型配置
            AiClientModel model = aiClientModelDao.selectByModelId(modelId);
            if (model != null && model.getStatus() == 1) {
                // 转换为VO对象
                AiClientModelVO modelVO = AiClientModelVO.builder()
                        .modelId(model.getModelId())
                        .apiId(model.getApiId())
                        .modelName(model.getModelName())
                        .modelType(model.getModelType())
                        .build();

                // 避免重复添加相同的模型配置
                if (result.stream().noneMatch(vo -> vo.getModelId().equals(modelVO.getModelId()))) {
                    result.add(modelVO);
                }
            }
        }

        return result;
    }
}
