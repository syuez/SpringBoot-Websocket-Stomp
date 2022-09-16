package com.syuez.springbootwebsocketstomp.service;

import com.syuez.springbootwebsocketstomp.common.MessageType;
import com.syuez.springbootwebsocketstomp.repository.SessionRedisRepository;
import com.syuez.springbootwebsocketstomp.session.SessionSubscribe;
import com.syuez.springbootwebsocketstomp.session.SimpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 定义用户连接或断开时相应的处理
 */
@Service
public class ConnectDisconnectProcessService {
    // 声明一个空的 sessionSubscribe
    SessionSubscribe sessionSubscribe;
    @Autowired
    private SessionRedisRepository repository;
    @Autowired
    private MessageType messageType;
    /**
     * 处理连接事件
     * @param subscriptionId 订阅 id
     * @param sessionId 会话 id
     */
    public synchronized void connect(String subscriptionId, String sessionId) {
        // 创建一个 SimpSession 对象
        SimpSession session = new SimpSession(sessionId);

        // 查询 Redis 是否已经存在 subscriptionId 对应的数据
        Optional<SessionSubscribe> optional = Optional.ofNullable(repository.findBySimpSubscriptionId(subscriptionId));

        if(optional.isEmpty()) {
            // 如果没有，新建一个
            sessionSubscribe = new SessionSubscribe(subscriptionId);
        } else {
            // 如果存在，则取出
            sessionSubscribe = optional.get();
        }
        sessionSubscribe.addSimpSession(session);
        repository.save(sessionSubscribe);
        messageType.getMessageService(subscriptionId).start();
    }
    /**
     * 处理断开事件
     * @param sessionId 会话 id
     */
    public synchronized void disconnect(String sessionId) {
        SimpSession session = new SimpSession(sessionId);
        // 查询 Redis 是否已经存在 sessionId 对应的 subscriptionId
        Optional<SessionSubscribe> optional = Optional.ofNullable(repository.findBySimpSessionList_SimpSessionId(sessionId));
        if(optional.isPresent()) {
            sessionSubscribe = optional.get();
            String subscriptionId = sessionSubscribe.getSimpSubscriptionId();
            sessionSubscribe.removeSimpSession(session);
            repository.save(sessionSubscribe);
            messageType.getMessageService(subscriptionId).stop();
        }
    }


}
