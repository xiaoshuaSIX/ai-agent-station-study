package com.xiaoshuai66.ai.infrastructure.dao;

import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 聊天模型配置表 DAO
 *
 * @Author 赵帅
 * @Create 2025/06/14 12:33
 */
@Mapper
public interface IAiClientModelDao {

    /**
     * 插入模型配置
     * @param aiClientModel 模型配置对象
     * @return 影响行数
     */
    int insert(AiClientModel aiClientModel);

    /**
     * 根据ID更新模型配置
     * @param aiClientModel 模型配置对象
     * @return 影响行数
     */
    int updateById(AiClientModel aiClientModel);

    /**
     * 根据ID删除模型配置
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据ID查询模型配置
     * @param id 主键ID
     * @return 模型配置对象
     */
    AiClientModel selectById(Long id);

    /**
     * 根据模型ID查询模型配置
     * @param modelId 模型ID
     * @return 模型配置对象
     */
    AiClientModel selectByModelId(String modelId);

    /**
     * 查询所有模型配置
     * @return 模型配置列表
     */
    List<AiClientModel> selectAll();
}