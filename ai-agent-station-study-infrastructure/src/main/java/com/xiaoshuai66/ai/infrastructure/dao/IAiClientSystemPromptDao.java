package com.xiaoshuai66.ai.infrastructure.dao;

import com.xiaoshuai66.ai.infrastructure.dao.po.AiClientSystemPrompt;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统提示词配置表 DAO
 *
 * @Author 赵帅
 * @Create 2025/06/14 12:46
 */
@Mapper
public interface IAiClientSystemPromptDao {

    /**
     * 插入系统提示词配置
     * @param aiClientSystemPrompt 系统提示词配置对象
     * @return 影响行数
     */
    int insert(AiClientSystemPrompt aiClientSystemPrompt);

    /**
     * 根据ID更新系统提示词配置
     * @param aiClientSystemPrompt 系统提示词配置对象
     * @return 影响行数
     */
    int updateById(AiClientSystemPrompt aiClientSystemPrompt);

    /**
     * 根据ID删除系统提示词配置
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据ID查询系统提示词配置
     * @param id 主键ID
     * @return 系统提示词配置对象
     */
    AiClientSystemPrompt selectById(Long id);

    /**
     * 根据提示词ID查询系统提示词配置
     * @param promptId 提示词ID
     * @return 系统提示词配置对象
     */
    AiClientSystemPrompt selectByPromptId(String promptId);

    /**
     * 查询所有系统提示词配置
     * @return 系统提示词配置列表
     */
    List<AiClientSystemPrompt> selectAll();
}