package com.xiaoshuai66.ai.domain.agent.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @Author 赵帅
 * @Create 2025/10/30 21:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiClientApiVO {
    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 全局唯一配置ID
     */
    private String apiId;

    /**
     * API基础URL
     */
    private String baseUrl;

    /**
     * API密钥
     */
    private String apiKey;

    /**
     * 补全API路径
     */
    private String completionsPath;

    /**
     * 嵌入API路径
     */
    private String embeddingsPath;
}
