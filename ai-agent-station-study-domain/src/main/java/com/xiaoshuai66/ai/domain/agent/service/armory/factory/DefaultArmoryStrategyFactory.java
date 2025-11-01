package com.xiaoshuai66.ai.domain.agent.service.armory.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 工厂类
 *
 * @Author 赵帅
 * @Create 2025/10/30 20:56
 */
@Service
public class DefaultArmoryStrategyFactory {


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {
        private Map<String, Object> dataObjects = new HashMap<>();

        public <T> void setValue(String key, T value) {
            dataObjects.put(key, value);
        }

        public <T> T getValues(String key) {
            return (T) dataObjects.get(key);
        }
    }
}
