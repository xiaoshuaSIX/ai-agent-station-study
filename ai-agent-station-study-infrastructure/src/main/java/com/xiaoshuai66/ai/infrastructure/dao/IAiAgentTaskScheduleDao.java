package com.xiaoshuai66.ai.infrastructure.dao;

import com.xiaoshuai66.ai.infrastructure.dao.po.AiAgentTaskSchedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 智能体任务调度配置表 DAO
 *
 * @Author 赵帅
 * @Create 2025/06/14 12:44
 */
@Mapper
public interface IAiAgentTaskScheduleDao {

    /**
     * 插入智能体任务调度配置
     * @param aiAgentTaskSchedule 智能体任务调度配置对象
     * @return 影响行数
     */
    int insert(AiAgentTaskSchedule aiAgentTaskSchedule);

    /**
     * 根据ID更新智能体任务调度配置
     * @param aiAgentTaskSchedule 智能体任务调度配置对象
     * @return 影响行数
     */
    int updateById(AiAgentTaskSchedule aiAgentTaskSchedule);

    /**
     * 根据ID删除智能体任务调度配置
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据ID查询智能体任务调度配置
     * @param id 主键ID
     * @return 智能体任务调度配置对象
     */
    AiAgentTaskSchedule selectById(Long id);

    /**
     * 根据智能体ID查询智能体任务调度配置列表
     * @param agentId 智能体ID
     * @return 智能体任务调度配置列表
     */
    List<AiAgentTaskSchedule> selectByAgentId(Long agentId);

    /**
     * 查询所有智能体任务调度配置
     * @return 智能体任务调度配置列表
     */
    List<AiAgentTaskSchedule> selectAll();
}