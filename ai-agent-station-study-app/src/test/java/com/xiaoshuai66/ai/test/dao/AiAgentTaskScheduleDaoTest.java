package com.xiaoshuai66.ai.test.dao;

import com.xiaoshuai66.ai.infrastructure.dao.IAiAgentTaskScheduleDao;
import com.xiaoshuai66.ai.infrastructure.dao.po.AiAgentTaskSchedule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AiAgentTaskScheduleDaoTest {

    @Autowired
    private IAiAgentTaskScheduleDao aiAgentTaskScheduleDao;

    @Test
    public void testInsert() {
        AiAgentTaskSchedule schedule = new AiAgentTaskSchedule();
        schedule.setAgentId(1L);
        schedule.setTaskName("test-task");
        schedule.setDescription("Test task description");
        schedule.setCronExpression("0 0 * * * ?");
        schedule.setTaskParam("{\"param\":\"value\"}");
        schedule.setStatus(1);
        schedule.setCreateTime(LocalDateTime.now());
        schedule.setUpdateTime(LocalDateTime.now());

        int result = aiAgentTaskScheduleDao.insert(schedule);
        
        assertEquals(1, result);
        assertNotNull(schedule.getId());
    }

    @Test
    public void testUpdateById() {
        AiAgentTaskSchedule schedule = new AiAgentTaskSchedule();
        schedule.setAgentId(1L);
        schedule.setTaskName("test-task");
        schedule.setDescription("Test task description");
        schedule.setCronExpression("0 0 * * * ?");
        schedule.setTaskParam("{\"param\":\"value\"}");
        schedule.setStatus(1);
        schedule.setCreateTime(LocalDateTime.now());
        schedule.setUpdateTime(LocalDateTime.now());
        
        aiAgentTaskScheduleDao.insert(schedule);
        
        schedule.setTaskName("updated-task");
        schedule.setDescription("Updated description");
        schedule.setUpdateTime(LocalDateTime.now());
        
        int result = aiAgentTaskScheduleDao.updateById(schedule);
        
        assertEquals(1, result);
        
        AiAgentTaskSchedule updated = aiAgentTaskScheduleDao.selectById(schedule.getId());
        assertEquals("updated-task", updated.getTaskName());
        assertEquals("Updated description", updated.getDescription());
    }

    @Test
    public void testDeleteById() {
        AiAgentTaskSchedule schedule = new AiAgentTaskSchedule();
        schedule.setAgentId(1L);
        schedule.setTaskName("test-task");
        schedule.setDescription("Test task description");
        schedule.setCronExpression("0 0 * * * ?");
        schedule.setTaskParam("{\"param\":\"value\"}");
        schedule.setStatus(1);
        schedule.setCreateTime(LocalDateTime.now());
        schedule.setUpdateTime(LocalDateTime.now());
        
        aiAgentTaskScheduleDao.insert(schedule);
        
        int result = aiAgentTaskScheduleDao.deleteById(schedule.getId());
        
        assertEquals(1, result);
        
        AiAgentTaskSchedule deleted = aiAgentTaskScheduleDao.selectById(schedule.getId());
        assertNull(deleted);
    }

    @Test
    public void testSelectById() {
        AiAgentTaskSchedule schedule = new AiAgentTaskSchedule();
        schedule.setAgentId(1L);
        schedule.setTaskName("test-task");
        schedule.setDescription("Test task description");
        schedule.setCronExpression("0 0 * * * ?");
        schedule.setTaskParam("{\"param\":\"value\"}");
        schedule.setStatus(1);
        schedule.setCreateTime(LocalDateTime.now());
        schedule.setUpdateTime(LocalDateTime.now());
        
        aiAgentTaskScheduleDao.insert(schedule);
        
        AiAgentTaskSchedule result = aiAgentTaskScheduleDao.selectById(schedule.getId());
        
        assertNotNull(result);
        assertEquals(schedule.getAgentId(), result.getAgentId());
        assertEquals(schedule.getTaskName(), result.getTaskName());
        assertEquals(schedule.getDescription(), result.getDescription());
    }

    @Test
    public void testSelectByAgentId() {
        AiAgentTaskSchedule schedule = new AiAgentTaskSchedule();
        schedule.setAgentId(1L);
        schedule.setTaskName("test-task");
        schedule.setDescription("Test task description");
        schedule.setCronExpression("0 0 * * * ?");
        schedule.setTaskParam("{\"param\":\"value\"}");
        schedule.setStatus(1);
        schedule.setCreateTime(LocalDateTime.now());
        schedule.setUpdateTime(LocalDateTime.now());
        
        aiAgentTaskScheduleDao.insert(schedule);
        
        List<AiAgentTaskSchedule> results = aiAgentTaskScheduleDao.selectByAgentId(1L);
        
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(schedule.getTaskName(), results.get(0).getTaskName());
    }

    @Test
    public void testSelectAll() {
        AiAgentTaskSchedule schedule1 = new AiAgentTaskSchedule();
        schedule1.setAgentId(1L);
        schedule1.setTaskName("test-task-1");
        schedule1.setDescription("Test task description 1");
        schedule1.setCronExpression("0 0 * * * ?");
        schedule1.setTaskParam("{\"param\":\"value1\"}");
        schedule1.setStatus(1);
        schedule1.setCreateTime(LocalDateTime.now());
        schedule1.setUpdateTime(LocalDateTime.now());
        
        AiAgentTaskSchedule schedule2 = new AiAgentTaskSchedule();
        schedule2.setAgentId(2L);
        schedule2.setTaskName("test-task-2");
        schedule2.setDescription("Test task description 2");
        schedule2.setCronExpression("0 0 * * * ?");
        schedule2.setTaskParam("{\"param\":\"value2\"}");
        schedule2.setStatus(1);
        schedule2.setCreateTime(LocalDateTime.now());
        schedule2.setUpdateTime(LocalDateTime.now());
        
        aiAgentTaskScheduleDao.insert(schedule1);
        aiAgentTaskScheduleDao.insert(schedule2);
        
        List<AiAgentTaskSchedule> results = aiAgentTaskScheduleDao.selectAll();
        
        assertFalse(results.isEmpty());
        assertTrue(results.size() >= 2);
    }
}