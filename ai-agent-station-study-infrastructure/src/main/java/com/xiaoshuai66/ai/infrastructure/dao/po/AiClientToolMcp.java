package com.xiaoshuai66.ai.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * MCP客户端配置表
 *
 * @Author 赵帅
 * @Create 2025/11/1 11:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiClientToolMcp {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * MCP ID
     */
    private String mcpId;

    /**
     * MCP名称
     */
    private String mcpName;

    /**
     * 传输类型(see/stdio)
     */
    private String transportType;

    /**
     * 传输配置(see/stdio)
     */
    private String transportConfig;

    /**
     * 请求超时时间(分钟)
     */
    private Integer requestTimeout;

    /**
     * 状态(0:禁用，1:启用)
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
