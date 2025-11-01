package com.xiaoshuai66.ai.domain.agent.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Agent 通用枚举类
 *
 * @Author 赵帅
 * @Create 2025/10/30 22:21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AiAgentEnumVO {

    AI_CLIENT("客户端", "client", "ai_client_", "aiClientLoadDataStrategy"),
    AI_CLIENT_MODEL("对话模型", "model", "ai_client_model_", "aiClientModelLoadDataStrategy"),

    ;

    /**
     * 名称
     */
    private String name;

    /**
     * code
     */
    private String code;

    /**
     * Bean 对象名称标签
     */
    private String beanNameTag;

    /**
     * 装配数据策略
     */
    private String loadDataStrategy;
}
