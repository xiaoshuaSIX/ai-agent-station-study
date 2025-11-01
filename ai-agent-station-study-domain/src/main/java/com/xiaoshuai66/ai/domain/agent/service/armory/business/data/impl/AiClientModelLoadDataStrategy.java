package com.xiaoshuai66.ai.domain.agent.service.armory.business.data.impl;

import com.xiaoshuai66.ai.domain.agent.adapter.repository.IAgentRepository;
import com.xiaoshuai66.ai.domain.agent.model.entity.ArmoryCommandEntity;
import com.xiaoshuai66.ai.domain.agent.model.valobj.AiClientApiVO;
import com.xiaoshuai66.ai.domain.agent.model.valobj.AiClientModelVO;
import com.xiaoshuai66.ai.domain.agent.service.armory.business.data.ILoadDataStrategy;
import com.xiaoshuai66.ai.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 以客户端对话模型，加载数据策略
 *
 * @Author 赵帅
 * @Create 2025/10/31 00:12
 */
@Slf4j
@Service
public class AiClientModelLoadDataStrategy implements ILoadDataStrategy {

    @Resource
    private IAgentRepository repository;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void loadData(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) {
        List<String> modelIdList = armoryCommandEntity.getCommandIdList();

        CompletableFuture<List<AiClientApiVO>> aiClientApiListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_api) {}", modelIdList);
            return repository.queryAiClientApiVOListByModelIds(modelIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientModelVO>> aiClientModelListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_model) {}", modelIdList);
            return repository.AiClientModelVOByModelIds(modelIdList);
        }, threadPoolExecutor);
    }
}
