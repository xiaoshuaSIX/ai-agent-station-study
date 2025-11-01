package com.xiaoshuai66.ai.domain.agent.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AI客户端配置，值对象
 *
 * @Author 赵帅
 * @Create 2025/11/1 19:04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiClientVO {

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 描述
     */
    private String description;

    /**
     * 全局唯一模型ID
     */
    private String modelId;

    /**
     * Prompt ID List
     */
    private List<String> promoptIdList;

    /**
     * MCP ID List
     */
    private List<String> mcpIdList;

    /**
     * 顾问ID List
     */
    private List<String> advisorIdList;
}
