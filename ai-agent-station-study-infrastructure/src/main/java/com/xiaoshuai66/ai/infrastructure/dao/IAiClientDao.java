package com.xiaoshuai66.ai.infrastructure.dao;

import com.xiaoshuai66.ai.infrastructure.dao.po.AiClient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI客户端配置表 DAO
 *
 * @Author 赵帅
 * @Create 2025/06/14 12:34
 */
@Mapper
public interface IAiClientDao {

    /**
     * 插入AI客户端配置
     * @param aiClient AI客户端配置对象
     * @return 影响行数
     */
    int insert(AiClient aiClient);

    /**
     * 根据ID更新AI客户端配置
     * @param aiClient AI客户端配置对象
     * @return 影响行数
     */
    int updateById(AiClient aiClient);

    /**
     * 根据ID删除AI客户端配置
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据ID查询AI客户端配置
     * @param id 主键ID
     * @return AI客户端配置对象
     */
    AiClient selectById(Long id);

    /**
     * 根据客户端ID查询AI客户端配置
     * @param clientId 客户端ID
     * @return AI客户端配置对象
     */
    AiClient selectByClientId(String clientId);

    /**
     * 查询所有AI客户端配置
     * @return AI客户端配置列表
     */
    List<AiClient> selectAll();
}