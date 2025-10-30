package com.xiaoshuai66.ai.test.dao;

import com.xiaoshuai66.ai.infrastructure.dao.IAiClientSystemPromptDao;
import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientSystemPrompt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AiClientSystemPromptDaoTest {

    @Autowired
    private IAiClientSystemPromptDao aiClientSystemPromptDao;

    @Test
    public void testCrudOperations() {
        // 创建测试对象
        AiClientSystemPrompt prompt = new AiClientSystemPrompt();
        prompt.setPromptId("test-prompt-id");
        prompt.setPromptName("Test Prompt");
        prompt.setPromptContent("This is a test prompt content");
        prompt.setDescription("Test description");
        prompt.setStatus(1);
        prompt.setCreateTime(LocalDateTime.now());
        prompt.setUpdateTime(LocalDateTime.now());

        // 测试插入
        int insertResult = aiClientSystemPromptDao.insert(prompt);
        assertEquals(1, insertResult);
        assertNotNull(prompt.getId());

        // 测试查询
        AiClientSystemPrompt queried = aiClientSystemPromptDao.selectById(prompt.getId());
        assertNotNull(queried);
        assertEquals(prompt.getPromptId(), queried.getPromptId());
        
        // 测试按promptId查询
        AiClientSystemPrompt promptByPromptId = aiClientSystemPromptDao.selectByPromptId("test-prompt-id");
        assertNotNull(promptByPromptId);
        
        // 测试更新
        prompt.setPromptName("Updated Prompt");
        int updateResult = aiClientSystemPromptDao.updateById(prompt);
        assertEquals(1, updateResult);
        
        AiClientSystemPrompt updated = aiClientSystemPromptDao.selectById(prompt.getId());
        assertEquals("Updated Prompt", updated.getPromptName());
        
        // 测试删除
        int deleteResult = aiClientSystemPromptDao.deleteById(prompt.getId());
        assertEquals(1, deleteResult);
        
        AiClientSystemPrompt deleted = aiClientSystemPromptDao.selectById(prompt.getId());
        assertNull(deleted);
        
        // 测试selectAll
        AiClientSystemPrompt prompt1 = new AiClientSystemPrompt();
        prompt1.setPromptId("test-prompt-id-1");
        prompt1.setPromptName("Test Prompt 1");
        prompt1.setPromptContent("Content 1");
        prompt1.setStatus(1);
        prompt1.setCreateTime(LocalDateTime.now());
        prompt1.setUpdateTime(LocalDateTime.now());
        
        AiClientSystemPrompt prompt2 = new AiClientSystemPrompt();
        prompt2.setPromptId("test-prompt-id-2");
        prompt2.setPromptName("Test Prompt 2");
        prompt2.setPromptContent("Content 2");
        prompt2.setStatus(1);
        prompt2.setCreateTime(LocalDateTime.now());
        prompt2.setUpdateTime(LocalDateTime.now());
        
        aiClientSystemPromptDao.insert(prompt1);
        aiClientSystemPromptDao.insert(prompt2);
        
        List<AiClientSystemPrompt> allResults = aiClientSystemPromptDao.selectAll();
        assertNotNull(allResults);
        assertTrue(allResults.size() >= 2);
    }
}