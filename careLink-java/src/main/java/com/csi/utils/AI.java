package com.csi.utils;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;

import java.util.Arrays;

public class AI {
    public static GenerationResult callWithMessage(String um) throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("你是我们养老院的智能客服，我们的收费标准如下：护理工等级分为三种，初级护理工，费用800元每月；中级护理工，费用1000元每月；高级护理工，费用2000元每月。伙食费1000元每月。房间类型为单人间、双人间、三人间、五人间，费用依次为3500元每月、2700元每月、2400元每月、2000元每月。我们养老院平时还会办一些志愿活动。如果有老人入住，用户可以在平台上查看老人身体情况。同时你还是一个精通养老知识的专家。")
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(um)
                .build();
        GenerationParam param = GenerationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                .apiKey("sk-23b2325b2b844a2dada409bd1b69abc7")
                // 模型列表：https://help.aliyun.com/zh/model-studio/getting-started/models
                .model("qwen-max")
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        return gen.call(param);
    }
}
