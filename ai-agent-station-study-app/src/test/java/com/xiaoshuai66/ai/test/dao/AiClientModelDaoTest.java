package com.xiaoshuai66.ai.test.dao;

import com.xiaoshuai66.ai.infrastructure.dao.IAiClientModelDao;
import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AiClientModelDaoTest {

    @Autowired
    private IAiClientModelDao aiClientModelDao;

    @Test
    public void testCrudOperations() {
        // 创建测试对象
        AiClientModel model = new AiClientModel();
        model.setModelId("test-model-id");
        model.setApiId("test-api-id");
        model.setModelName("Test Model");
        model.setModelType("test-type");
        model.setStatus(1);
        model.setCreateTime(LocalDateTime.now());
        model.setUpdateTime(LocalDateTime.now());

        // 测试插入
        int insertResult = aiClientModelDao.insert(model);
        assertEquals(1, insertResult);
        assertNotNull(model.getId());

        // 测试查询
        AiClientModel queried = aiClientModelDao.selectById(model.getId());
        assertNotNull(queried);
        assertEquals(model.getModelId(), queried.getModelId());
        
        // 测试按modelId查询
        AiClientModel modelByModelId = aiClientModelDao.selectByModelId("test-model-id");
        assertNotNull(modelByModelId);
        
        // 测试更新
        model.setModelName("Updated Model");
        int updateResult = aiClientModelDao.updateById(model);
        assertEquals(1, updateResult);
        
        AiClientModel updated = aiClientModelDao.selectById(model.getId());
        assertEquals("Updated Model", updated.getModelName());
        
        // 测试删除
        int deleteResult = aiClientModelDao.deleteById(model.getId());
        assertEquals(1, deleteResult);
        
        AiClientModel deleted = aiClientModelDao.selectById(model.getId());
        assertNull(deleted);
        
        // 测试selectAll
        AiClientModel model1 = new AiClientModel();
        model1.setModelId("test-model-id-1");
        model1.setApiId("test-api-id-1");
        model1.setModelName("Test Model 1");
        model1.setStatus(1);
        model1.setCreateTime(LocalDateTime.now());
        model1.setUpdateTime(LocalDateTime.now());
        
        AiClientModel model2 = new AiClientModel();
        model2.setModelId("test-model-id-2");
        model2.setApiId("test-api-id-2");
        model2.setModelName("Test Model 2");
        model2.setStatus(1);
        model2.setCreateTime(LocalDateTime.now());
        model2.setUpdateTime(LocalDateTime.now());
        
        aiClientModelDao.insert(model1);
        aiClientModelDao.insert(model2);
        
        List<AiClientModel> allResults = aiClientModelDao.selectAll();
        assertNotNull(allResults);
        assertTrue(allResults.size() >= 2);
    }
}