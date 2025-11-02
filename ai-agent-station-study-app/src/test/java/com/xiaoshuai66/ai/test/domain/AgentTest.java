package com.xiaoshuai66.ai.test.domain;


import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.xiaoshuai66.ai.domain.agent.model.entity.ArmoryCommandEntity;
import com.xiaoshuai66.ai.domain.agent.model.valobj.AiAgentEnumVO;
import com.xiaoshuai66.ai.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AgentTest {

    @Resource
    private DefaultArmoryStrategyFactory defaultArmoryStrategyFactory;

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void test_aiClientApiNode() throws Exception {
        StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> armoryStrategyHandler =
                defaultArmoryStrategyFactory.armoryStrategyHandler();

        String apply = armoryStrategyHandler.apply(
                ArmoryCommandEntity.builder()
                        .commandType(AiAgentEnumVO.AI_CLIENT.getCode())
                        .commandIdList(Arrays.asList("3001"))
                        .build(),
                new DefaultArmoryStrategyFactory.DynamicContext()
        );

        OpenAiApi openAiApi = (OpenAiApi) applicationContext.getBean(AiAgentEnumVO.AI_CLIENT_API.getBeanName("1001"));
        log.info("测试结果：{}", openAiApi);
    }
}
