package com.xiaoshuai66.ai.domain.agent.service.armory.factory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.xiaoshuai66.ai.domain.agent.model.entity.ArmoryCommandEntity;
import com.xiaoshuai66.ai.domain.agent.service.armory.RootNode;
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

    private final RootNode rootNode;

    public DefaultArmoryStrategyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> armoryStrategyHandler() {
        return rootNode;
    }


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
