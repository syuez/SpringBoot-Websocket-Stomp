package com.syuez.springbootwebsocketstomp.event;

import com.syuez.springbootwebsocketstomp.service.ConnectDisconnectProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * 用户订阅事件
 */
@Component
public class SessionSubscribeListenerEvent implements ApplicationListener<SessionSubscribeEvent> {

    @Autowired
    private ConnectDisconnectProcessService service;

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        String subscriptionId = (String) event.getMessage().getHeaders().get("simpSubscriptionId");
        String sessionId = (String) event.getMessage().getHeaders().get("simpSessionId");

        Thread t = new Thread(() -> service.connect(subscriptionId, sessionId));
        t.start();
    }
}
