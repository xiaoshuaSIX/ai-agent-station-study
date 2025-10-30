package com.xiaoshuai66.ai.infrastructure.dao;

import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientApi;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * OpenAI API配置表 DAO
 *
 * @Author 赵帅
 * @Create 2025/06/14 12:33
 */
@Mapper
public interface IAiClientApiDao {

    /**
     * 插入API配置
     * @param aiClientApi API配置对象
     * @return 影响行数
     */
    int insert(AiClientApi aiClientApi);

    /**
     * 根据ID更新API配置
     * @param aiClientApi API配置对象
     * @return 影响行数
     */
    int updateById(AiClientApi aiClientApi);

    /**
     * 根据ID删除API配置
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据ID查询API配置
     * @param id 主键ID
     * @return API配置对象
     */
    AiClientApi selectById(Long id);

    /**
     * 根据API ID查询API配置
     * @param apiId API ID
     * @return API配置对象
     */
    AiClientApi selectByApiId(String apiId);

    /**
     * 查询所有API配置
     * @return API配置列表
     */
    List<AiClientApi> selectAll();
}