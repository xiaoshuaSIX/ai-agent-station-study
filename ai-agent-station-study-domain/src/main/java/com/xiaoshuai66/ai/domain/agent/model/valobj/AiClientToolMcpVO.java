package com.xiaoshuai66.ai.domain.agent.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * MCP客户端配置，值对象
 *
 * @Author 赵帅
 * @Create 2025/11/1 10:58
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiClientToolMcpVO {

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
     * 传输配置 - see
     */
    private TransportConfigSee transportConfigSee;

    /**
     * 传输配置 - stdio
     */
    private TransportConfigStdio transportConfigStdio;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransportConfigSee {
        private String baseUri;
        private String sseEndpoint;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransportConfigStdio {
        private Map<String, Stdio> stdio;

        @Data
        public static class Stdio {
            private String command;
            private List<String> args;
            private Map<String, String> env;
        }
    }
}
