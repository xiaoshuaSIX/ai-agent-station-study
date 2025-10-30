package com.xiaoshuai66.ai.infrastructure.dao;

import com.xiaoshuai66.ai.infrastructure.dao.po.AiAgentFlowConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 智能体-客户端关联表 DAO
 *
 * @Author 赵帅
 * @Create 2025/06/14 12:42
 */
@Mapper
public interface IAiAgentFlowConfigDao {

    /**
     * 插入智能体-客户端关联配置
     * @param aiAgentFlowConfig 智能体-客户端关联配置对象
     * @return 影响行数
     */
    int insert(AiAgentFlowConfig aiAgentFlowConfig);

    /**
     * 根据ID更新智能体-客户端关联配置
     * @param aiAgentFlowConfig 智能体-客户端关联配置对象
     * @return 影响行数
     */
    int updateById(AiAgentFlowConfig aiAgentFlowConfig);

    /**
     * 根据ID删除智能体-客户端关联配置
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据ID查询智能体-客户端关联配置
     * @param id 主键ID
     * @return 智能体-客户端关联配置对象
     */
    AiAgentFlowConfig selectById(Long id);

    /**
     * 根据智能体ID查询智能体-客户端关联配置列表
     * @param agentId 智能体ID
     * @return 智能体-客户端关联配置列表
     */
    List<AiAgentFlowConfig> selectByAgentId(Long agentId);

    /**
     * 查询所有智能体-客户端关联配置
     * @return 智能体-客户端关联配置列表
     */
    List<AiAgentFlowConfig> selectAll();
}