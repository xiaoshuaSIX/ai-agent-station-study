package com.xiaoshuai66.ai.infrastructure.dao;

import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI客户端统一关联配置表 DAO
 *
 * @Author 赵帅
 * @Create 2025/06/14 12:46
 */
@Mapper
public interface IAiClientConfigDao {

    /**
     * 插入客户端关联配置
     * @param aiClientConfig 客户端关联配置对象
     * @return 影响行数
     */
    int insert(AiClientConfig aiClientConfig);

    /**
     * 根据ID更新客户端关联配置
     * @param aiClientConfig 客户端关联配置对象
     * @return 影响行数
     */
    int updateById(AiClientConfig aiClientConfig);

    /**
     * 根据ID删除客户端关联配置
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据ID查询客户端关联配置
     * @param id 主键ID
     * @return 客户端关联配置对象
     */
    AiClientConfig selectById(Long id);

    /**
     * 根据源ID和源类型查询客户端关联配置列表
     * @param sourceId 源ID
     * @param sourceType 源类型
     * @return 客户端关联配置列表
     */
    List<AiClientConfig> selectBySourceIdAndType(String sourceId, String sourceType);

    /**
     * 根据目标ID和目标类型查询客户端关联配置列表
     * @param targetId 目标ID
     * @param targetType 目标类型
     * @return 客户端关联配置列表
     */
    List<AiClientConfig> selectByTargetIdAndType(String targetId, String targetType);

    /**
     * 查询所有客户端关联配置
     * @return 客户端关联配置列表
     */
    List<AiClientConfig> selectAll();
}