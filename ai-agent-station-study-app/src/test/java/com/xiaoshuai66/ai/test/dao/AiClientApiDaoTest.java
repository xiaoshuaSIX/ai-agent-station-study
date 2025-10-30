package com.xiaoshuai66.ai.test.dao;

import com.xiaoshuai66.ai.infrastructure.dao.IAiClientApiDao;
import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AiClientApiDaoTest {

    @Autowired
    private IAiClientApiDao aiClientApiDao;

    @Test
    public void testCrudOperations() {
        // 创建测试对象
        AiClientApi api = new AiClientApi();
        api.setApiId("test-api-id");
        api.setBaseUrl("https://api.test.com");
        api.setApiKey("test-api-key");
        api.setCompletionsPath("/v1/completions");
        api.setEmbeddingsPath("/v1/embeddings");
        api.setStatus(1);
        api.setCreateTime(LocalDateTime.now());
        api.setUpdateTime(LocalDateTime.now());

        // 测试插入
        int insertResult = aiClientApiDao.insert(api);
        assertEquals(1, insertResult);
        assertNotNull(api.getId());

        // 测试查询
        AiClientApi queried = aiClientApiDao.selectById(api.getId());
        assertNotNull(queried);
        assertEquals(api.getApiId(), queried.getApiId());
        
        // 测试按apiId查询
        AiClientApi apiByApiId = aiClientApiDao.selectByApiId("test-api-id");
        assertNotNull(apiByApiId);
        
        // 测试更新
        api.setBaseUrl("https://api.updated.com");
        int updateResult = aiClientApiDao.updateById(api);
        assertEquals(1, updateResult);
        
        AiClientApi updated = aiClientApiDao.selectById(api.getId());
        assertEquals("https://api.updated.com", updated.getBaseUrl());
        
        // 测试删除
        int deleteResult = aiClientApiDao.deleteById(api.getId());
        assertEquals(1, deleteResult);
        
        AiClientApi deleted = aiClientApiDao.selectById(api.getId());
        assertNull(deleted);
        
        // 测试selectAll
        AiClientApi api1 = new AiClientApi();
        api1.setApiId("test-api-id-1");
        api1.setBaseUrl("https://api1.test.com");
        api1.setApiKey("test-api-key-1");
        api1.setStatus(1);
        api1.setCreateTime(LocalDateTime.now());
        api1.setUpdateTime(LocalDateTime.now());
        
        AiClientApi api2 = new AiClientApi();
        api2.setApiId("test-api-id-2");
        api2.setBaseUrl("https://api2.test.com");
        api2.setApiKey("test-api-key-2");
        api2.setStatus(1);
        api2.setCreateTime(LocalDateTime.now());
        api2.setUpdateTime(LocalDateTime.now());
        
        aiClientApiDao.insert(api1);
        aiClientApiDao.insert(api2);
        
        List<AiClientApi> allResults = aiClientApiDao.selectAll();
        assertNotNull(allResults);
        assertTrue(allResults.size() >= 2);
    }
}