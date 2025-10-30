package com.xiaoshuai66.ai.test.dao;

import com.xiaoshuai66.ai.infrastructure.dao.IAiClientAdvisorDao;
import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientAdvisor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AiClientAdvisorDaoTest {

    @Autowired
    private IAiClientAdvisorDao aiClientAdvisorDao;

    @Test
    public void testCrudOperations() {
        // 创建测试对象
        AiClientAdvisor advisor = new AiClientAdvisor();
        advisor.setAdvisorId("test-advisor-id");
        advisor.setAdvisorName("Test Advisor");
        advisor.setAdvisorType("test-type");
        advisor.setOrderNum(1);
        advisor.setExtParam("{\"param\":\"value\"}");
        advisor.setStatus(1);
        advisor.setCreateTime(LocalDateTime.now());
        advisor.setUpdateTime(LocalDateTime.now());

        // 测试插入
        int insertResult = aiClientAdvisorDao.insert(advisor);
        assertEquals(1, insertResult);
        assertNotNull(advisor.getId());

        // 测试查询
        AiClientAdvisor queried = aiClientAdvisorDao.selectById(advisor.getId());
        assertNotNull(queried);
        assertEquals(advisor.getAdvisorId(), queried.getAdvisorId());
        
        // 测试按advisorId查询
        AiClientAdvisor queriedByAdvisorId = aiClientAdvisorDao.selectByAdvisorId("test-advisor-id");
        assertNotNull(queriedByAdvisorId);
        
        // 测试更新
        advisor.setAdvisorName("Updated Advisor");
        int updateResult = aiClientAdvisorDao.updateById(advisor);
        assertEquals(1, updateResult);
        
        AiClientAdvisor updated = aiClientAdvisorDao.selectById(advisor.getId());
        assertEquals("Updated Advisor", updated.getAdvisorName());
        
        // 测试删除
        int deleteResult = aiClientAdvisorDao.deleteById(advisor.getId());
        assertEquals(1, deleteResult);
        
        AiClientAdvisor deleted = aiClientAdvisorDao.selectById(advisor.getId());
        assertNull(deleted);
        
        // 测试selectAll
        AiClientAdvisor advisor1 = new AiClientAdvisor();
        advisor1.setAdvisorId("test-advisor-id-1");
        advisor1.setAdvisorName("Test Advisor 1");
        advisor1.setAdvisorType("test-type");
        advisor1.setOrderNum(1);
        advisor1.setStatus(1);
        advisor1.setCreateTime(LocalDateTime.now());
        advisor1.setUpdateTime(LocalDateTime.now());
        
        AiClientAdvisor advisor2 = new AiClientAdvisor();
        advisor2.setAdvisorId("test-advisor-id-2");
        advisor2.setAdvisorName("Test Advisor 2");
        advisor2.setAdvisorType("test-type");
        advisor2.setOrderNum(2);
        advisor2.setStatus(1);
        advisor2.setCreateTime(LocalDateTime.now());
        advisor2.setUpdateTime(LocalDateTime.now());
        
        aiClientAdvisorDao.insert(advisor1);
        aiClientAdvisorDao.insert(advisor2);
        
        List<AiClientAdvisor> allResults = aiClientAdvisorDao.selectAll();
        assertNotNull(allResults);
        assertTrue(allResults.size() >= 2);
    }
}