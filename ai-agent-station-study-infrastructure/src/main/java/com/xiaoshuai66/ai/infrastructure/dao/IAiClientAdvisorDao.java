package com.xiaoshuai66.ai.infrastructure.dao;

import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientAdvisor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 顾问配置表 DAO
 *
 * @Author 赵帅
 * @Create 2025/06/14 12:35
 */
@Mapper
public interface IAiClientAdvisorDao {

    /**
     * 插入顾问配置
     * @param aiClientAdvisor 顾问配置对象
     * @return 影响行数
     */
    int insert(AiClientAdvisor aiClientAdvisor);

    /**
     * 根据ID更新顾问配置
     * @param aiClientAdvisor 顾问配置对象
     * @return 影响行数
     */
    int updateById(AiClientAdvisor aiClientAdvisor);

    /**
     * 根据ID删除顾问配置
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据ID查询顾问配置
     * @param id 主键ID
     * @return 顾问配置对象
     */
    AiClientAdvisor selectById(Long id);

    /**
     * 根据顾问ID查询顾问配置
     * @param advisorId 顾问ID
     * @return 顾问配置对象
     */
    AiClientAdvisor selectByAdvisorId(String advisorId);

    /**
     * 查询所有顾问配置
     * @return 顾问配置列表
     */
    List<AiClientAdvisor> selectAll();
}