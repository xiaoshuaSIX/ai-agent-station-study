package com.xiaoshuai66.ai.test.dao;

import com.xiaoshuai66.ai.infrastructure.dao.IAiClientConfigDao;
import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AiClientConfigDaoTest {

    @Autowired
    private IAiClientConfigDao aiClientConfigDao;

    @Test
    public void testCrudOperations() {
        // 创建测试对象
        AiClientConfig config = new AiClientConfig();
        config.setSourceType("client");
        config.setSourceId("source-id");
        config.setTargetType("model");
        config.setTargetId("target-id");
        config.setExtParam("{\"param\":\"value\"}");
        config.setStatus(1);
        config.setCreateTime(LocalDateTime.now());
        config.setUpdateTime(LocalDateTime.now());

        // 测试插入
        int insertResult = aiClientConfigDao.insert(config);
        assertEquals(1, insertResult);
        assertNotNull(config.getId());

        // 测试查询
        AiClientConfig queried = aiClientConfigDao.selectById(config.getId());
        assertNotNull(queried);
        assertEquals(config.getSourceId(), queried.getSourceId());
        
        // 测试按sourceId和sourceType查询
        List<AiClientConfig> configsBySource = aiClientConfigDao.selectBySourceIdAndType("source-id", "client");
        assertFalse(configsBySource.isEmpty());
        
        // 测试按targetId和targetType查询
        List<AiClientConfig> configsByTarget = aiClientConfigDao.selectByTargetIdAndType("target-id", "model");
        assertFalse(configsByTarget.isEmpty());
        
        // 测试更新
        config.setExtParam("{\"param\":\"updated\"}");
        int updateResult = aiClientConfigDao.updateById(config);
        assertEquals(1, updateResult);
        
        AiClientConfig updated = aiClientConfigDao.selectById(config.getId());
        assertEquals("{\"param\":\"updated\"}", updated.getExtParam());
        
        // 测试删除
        int deleteResult = aiClientConfigDao.deleteById(config.getId());
        assertEquals(1, deleteResult);
        
        AiClientConfig deleted = aiClientConfigDao.selectById(config.getId());
        assertNull(deleted);
        
        // 测试selectAll
        AiClientConfig config1 = new AiClientConfig();
        config1.setSourceType("client");
        config1.setSourceId("source-id-1");
        config1.setTargetType("model");
        config1.setTargetId("target-id-1");
        config1.setStatus(1);
        config1.setCreateTime(LocalDateTime.now());
        config1.setUpdateTime(LocalDateTime.now());
        
        AiClientConfig config2 = new AiClientConfig();
        config2.setSourceType("client");
        config2.setSourceId("source-id-2");
        config2.setTargetType("model");
        config2.setTargetId("target-id-2");
        config2.setStatus(1);
        config2.setCreateTime(LocalDateTime.now());
        config2.setUpdateTime(LocalDateTime.now());
        
        aiClientConfigDao.insert(config1);
        aiClientConfigDao.insert(config2);
        
        List<AiClientConfig> allResults = aiClientConfigDao.selectAll();
        assertNotNull(allResults);
        assertTrue(allResults.size() >= 2);
    }
}