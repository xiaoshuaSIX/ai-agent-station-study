package com.xiaoshuai66.ai.test.dao;

import com.xiaoshuai66.ai.infrastructure.dao.IAiAgentFlowConfigDao;
import com.xiaoshuai66.ai.infrastructure.dao.po.AiAgentFlowConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
public class AiAgentFlowConfigDaoTest {

    @Autowired
    private IAiAgentFlowConfigDao aiAgentFlowConfigDao;

    @Test
    public void testInsert() {
        // 准备测试数据
        AiAgentFlowConfig config = new AiAgentFlowConfig();
        config.setAgentId(1L);
        config.setClientId(3001L);
        config.setSequence(1);
        config.setCreateTime(LocalDateTime.now());

        // 执行插入操作
        int result = aiAgentFlowConfigDao.insert(config);
        
        // 验证结果
        assertEquals(1, result);
        assertNotNull(config.getId());
    }

    @Test
    public void testUpdateById() {
        // 准备测试数据
        AiAgentFlowConfig config = new AiAgentFlowConfig();
        config.setAgentId(1L);
        config.setClientId(3001L);
        config.setSequence(1);
        config.setCreateTime(LocalDateTime.now());
        
        // 先插入数据
        aiAgentFlowConfigDao.insert(config);
        
        // 修改数据
        config.setClientId(3002L);
        config.setSequence(2);
        
        // 执行更新操作
        int result = aiAgentFlowConfigDao.updateById(config);
        
        // 验证结果
        assertEquals(1, result);
        
        // 查询验证
        AiAgentFlowConfig updated = aiAgentFlowConfigDao.selectById(config.getId());
        assertEquals(3002L, updated.getClientId());
        assertEquals(2, updated.getSequence());
    }

    @Test
    public void testDeleteById() {
        // 准备测试数据
        AiAgentFlowConfig config = new AiAgentFlowConfig();
        config.setAgentId(1L);
        config.setClientId(3001L);
        config.setSequence(1);
        config.setCreateTime(LocalDateTime.now());
        
        // 先插入数据
        aiAgentFlowConfigDao.insert(config);
        
        // 执行删除操作
        int result = aiAgentFlowConfigDao.deleteById(config.getId());
        
        // 验证结果
        assertEquals(1, result);
        
        // 查询验证
        AiAgentFlowConfig deleted = aiAgentFlowConfigDao.selectById(config.getId());
        assertNull(deleted);
    }

    @Test
    public void testSelectById() {
        AiAgentFlowConfig aiAgentFlowConfig = aiAgentFlowConfigDao.selectById(1L);
        log.info("根据ID查询结果: {}", aiAgentFlowConfig);
    }

    @Test
    public void testSelectByAgentId() {
        // 准备测试数据
        AiAgentFlowConfig config = new AiAgentFlowConfig();
        config.setAgentId(1L);
        config.setClientId(3001L);
        config.setSequence(1);
        config.setCreateTime(LocalDateTime.now());
        
        // 先插入数据
        aiAgentFlowConfigDao.insert(config);
        
        // 执行查询操作
        List<AiAgentFlowConfig> results = aiAgentFlowConfigDao.selectByAgentId(1L);
        
        // 验证结果
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(config.getClientId(), results.get(0).getClientId());
    }

    @Test
    public void testSelectAll() {
        // 准备测试数据
        AiAgentFlowConfig config1 = new AiAgentFlowConfig();
        config1.setAgentId(1L);
        config1.setClientId(3001L);
        config1.setSequence(1);
        config1.setCreateTime(LocalDateTime.now());
        
        AiAgentFlowConfig config2 = new AiAgentFlowConfig();
        config2.setAgentId(2L);
        config2.setClientId(3002L);
        config2.setSequence(2);
        config2.setCreateTime(LocalDateTime.now());
        
        // 先插入数据
        aiAgentFlowConfigDao.insert(config1);
        aiAgentFlowConfigDao.insert(config2);
        
        // 执行查询操作
        List<AiAgentFlowConfig> results = aiAgentFlowConfigDao.selectAll();
        
        // 验证结果
        assertFalse(results.isEmpty());
        assertTrue(results.size() >= 2);
    }
}