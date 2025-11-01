package com.xiaoshuai66.ai.domain.agent.service.armory.business.data;

import com.xiaoshuai66.ai.domain.agent.model.entity.ArmoryCommandEntity;
import com.xiaoshuai66.ai.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;

/**
 * 数据加载策略
 *
 * @Author 赵帅
 * @Create 2025/10/30 21:22
 */
public interface ILoadDataStrategy {

    void loadData(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext);

}
