package com.syuez.springbootwebsocketstomp.event;

import com.syuez.springbootwebsocketstomp.service.ConnectDisconnectProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
/**
 * 用户断开事件
 */
@Component
public class DisconnectListenerEvent implements ApplicationListener<SessionDisconnectEvent> {

    @Autowired
    private ConnectDisconnectProcessService service;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();

        Thread t = new Thread(() -> service.disconnect(sessionId));
        t.start();
    }
}
