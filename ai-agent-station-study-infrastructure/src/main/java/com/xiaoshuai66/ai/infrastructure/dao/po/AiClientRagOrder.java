package com.xiaoshuai66.ai.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 知识库配置表
 *
 * @Author 赵帅
 * @Create 2025/06/14 12:44
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiClientRagOrder {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 知识库ID
     */
    private String ragId;

    /**
     * 知识库名称
     */
    private String ragName;

    /**
     * 知识标签
     */
    private String knowledgeTag;

    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}