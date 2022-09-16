package com.syuez.springbootwebsocketstomp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * SpringBoot通过不同的策略动态调用不同的实现类
 * https://blog.csdn.net/qq_38377774/article/details/110422906
 */
@Component
public class MessageType {
    @Autowired
    Map<String, SendMessage> messageMap;
    /**
     * 通过传入的不同参数返回相应的实现类
     * @param type Class 类型
     * @return 具体的实现类
     */
    public SendMessage getMessageService(String type) {
        return messageMap.get(type);
    }
}
