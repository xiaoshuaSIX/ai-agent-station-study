package com.xiaoshuai66.ai.test.dao;

import com.xiaoshuai66.ai.infrastructure.dao.IAiClientDao;
import com.xiaoshuai66.ai.infrastructure.dao.po.AiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AiClientDaoTest {

    @Autowired
    private IAiClientDao aiClientDao;

    @Test
    public void testInsert() {
        AiClient client = new AiClient();
        client.setClientId("test-client-id");
        client.setClientName("Test Client");
        client.setDescription("Test client description");
        client.setStatus(1);
        client.setCreateTime(LocalDateTime.now());
        client.setUpdateTime(LocalDateTime.now());

        int result = aiClientDao.insert(client);
        
        assertEquals(1, result);
        assertNotNull(client.getId());
    }

    @Test
    public void testUpdateById() {
        AiClient client = new AiClient();
        client.setClientId("test-client-id");
        client.setClientName("Test Client");
        client.setDescription("Test client description");
        client.setStatus(1);
        client.setCreateTime(LocalDateTime.now());
        client.setUpdateTime(LocalDateTime.now());
        
        aiClientDao.insert(client);
        
        client.setClientName("Updated Client");
        client.setDescription("Updated description");
        client.setUpdateTime(LocalDateTime.now());
        
        int result = aiClientDao.updateById(client);
        
        assertEquals(1, result);
        
        AiClient updated = aiClientDao.selectById(client.getId());
        assertEquals("Updated Client", updated.getClientName());
        assertEquals("Updated description", updated.getDescription());
    }

    @Test
    public void testDeleteById() {
        AiClient client = new AiClient();
        client.setClientId("test-client-id");
        client.setClientName("Test Client");
        client.setDescription("Test client description");
        client.setStatus(1);
        client.setCreateTime(LocalDateTime.now());
        client.setUpdateTime(LocalDateTime.now());
        
        aiClientDao.insert(client);
        
        int result = aiClientDao.deleteById(client.getId());
        
        assertEquals(1, result);
        
        AiClient deleted = aiClientDao.selectById(client.getId());
        assertNull(deleted);
    }

    @Test
    public void testSelectById() {
        AiClient client = new AiClient();
        client.setClientId("test-client-id");
        client.setClientName("Test Client");
        client.setDescription("Test client description");
        client.setStatus(1);
        client.setCreateTime(LocalDateTime.now());
        client.setUpdateTime(LocalDateTime.now());
        
        aiClientDao.insert(client);
        
        AiClient result = aiClientDao.selectById(client.getId());
        
        assertNotNull(result);
        assertEquals(client.getClientId(), result.getClientId());
        assertEquals(client.getClientName(), result.getClientName());
        assertEquals(client.getDescription(), result.getDescription());
    }

    @Test
    public void testSelectByClientId() {
        AiClient client = new AiClient();
        client.setClientId("test-client-id");
        client.setClientName("Test Client");
        client.setDescription("Test client description");
        client.setStatus(1);
        client.setCreateTime(LocalDateTime.now());
        client.setUpdateTime(LocalDateTime.now());
        
        aiClientDao.insert(client);
        
        AiClient result = aiClientDao.selectByClientId("test-client-id");
        
        assertNotNull(result);
        assertEquals(client.getClientName(), result.getClientName());
        assertEquals(client.getDescription(), result.getDescription());
    }

    @Test
    public void testSelectAll() {
        AiClient client1 = new AiClient();
        client1.setClientId("test-client-id-1");
        client1.setClientName("Test Client 1");
        client1.setDescription("Test client description 1");
        client1.setStatus(1);
        client1.setCreateTime(LocalDateTime.now());
        client1.setUpdateTime(LocalDateTime.now());
        
        AiClient client2 = new AiClient();
        client2.setClientId("test-client-id-2");
        client2.setClientName("Test Client 2");
        client2.setDescription("Test client description 2");
        client2.setStatus(1);
        client2.setCreateTime(LocalDateTime.now());
        client2.setUpdateTime(LocalDateTime.now());
        
        aiClientDao.insert(client1);
        aiClientDao.insert(client2);
        
        List<AiClient> results = aiClientDao.selectAll();
        
        assertNotNull(results);
        assertTrue(results.size() >= 2);
    }
}