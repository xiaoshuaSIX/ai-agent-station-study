package com.xiaoshuai66.ai.infrastructure.dao;

import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientRagOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 知识库配置表 DAO
 *
 * @Author 赵帅
 * @Create 2025/06/14 12:44
 */
@Mapper
public interface IAiClientRagOrderDao {

    /**
     * 插入知识库配置
     * @param aiClientRagOrder 知识库配置对象
     * @return 影响行数
     */
    int insert(AiClientRagOrder aiClientRagOrder);

    /**
     * 根据ID更新知识库配置
     * @param aiClientRagOrder 知识库配置对象
     * @return 影响行数
     */
    int updateById(AiClientRagOrder aiClientRagOrder);

    /**
     * 根据ID删除知识库配置
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据ID查询知识库配置
     * @param id 主键ID
     * @return 知识库配置对象
     */
    AiClientRagOrder selectById(Long id);

    /**
     * 根据知识库ID查询知识库配置
     * @param ragId 知识库ID
     * @return 知识库配置对象
     */
    AiClientRagOrder selectByRagId(String ragId);

    /**
     * 查询所有知识库配置
     * @return 知识库配置列表
     */
    List<AiClientRagOrder> selectAll();
}