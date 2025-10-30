package com.xiaoshuai66.ai.test.dao;

import com.xiaoshuai66.ai.infrastructure.dao.IAiClientRagOrderDao;
import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientRagOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AiClientRagOrderDaoTest {

    @Autowired
    private IAiClientRagOrderDao aiClientRagOrderDao;

    @Test
    public void testCrudOperations() {
        // 创建测试对象
        AiClientRagOrder ragOrder = new AiClientRagOrder();
        ragOrder.setRagId("test-rag-id");
        ragOrder.setRagName("Test RAG");
        ragOrder.setKnowledgeTag("test-tag");
        ragOrder.setStatus(1);
        ragOrder.setCreateTime(LocalDateTime.now());
        ragOrder.setUpdateTime(LocalDateTime.now());

        // 测试插入
        int insertResult = aiClientRagOrderDao.insert(ragOrder);
        assertEquals(1, insertResult);
        assertNotNull(ragOrder.getId());

        // 测试查询
        AiClientRagOrder queried = aiClientRagOrderDao.selectById(ragOrder.getId());
        assertNotNull(queried);
        assertEquals(ragOrder.getRagId(), queried.getRagId());
        
        // 测试按ragId查询
        AiClientRagOrder ragByRagId = aiClientRagOrderDao.selectByRagId("test-rag-id");
        assertNotNull(ragByRagId);
        
        // 测试更新
        ragOrder.setRagName("Updated RAG");
        int updateResult = aiClientRagOrderDao.updateById(ragOrder);
        assertEquals(1, updateResult);
        
        AiClientRagOrder updated = aiClientRagOrderDao.selectById(ragOrder.getId());
        assertEquals("Updated RAG", updated.getRagName());
        
        // 测试删除
        int deleteResult = aiClientRagOrderDao.deleteById(ragOrder.getId());
        assertEquals(1, deleteResult);
        
        AiClientRagOrder deleted = aiClientRagOrderDao.selectById(ragOrder.getId());
        assertNull(deleted);
        
        // 测试selectAll
        AiClientRagOrder rag1 = new AiClientRagOrder();
        rag1.setRagId("test-rag-id-1");
        rag1.setRagName("Test RAG 1");
        rag1.setStatus(1);
        rag1.setCreateTime(LocalDateTime.now());
        rag1.setUpdateTime(LocalDateTime.now());
        
        AiClientRagOrder rag2 = new AiClientRagOrder();
        rag2.setRagId("test-rag-id-2");
        rag2.setRagName("Test RAG 2");
        rag2.setStatus(1);
        rag2.setCreateTime(LocalDateTime.now());
        rag2.setUpdateTime(LocalDateTime.now());
        
        aiClientRagOrderDao.insert(rag1);
        aiClientRagOrderDao.insert(rag2);
        
        List<AiClientRagOrder> allResults = aiClientRagOrderDao.selectAll();
        assertNotNull(allResults);
        assertTrue(allResults.size() >= 2);
    }
}