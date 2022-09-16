package com.syuez.springbootwebsocketstomp.session;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

/**
 * Websocket 用户订阅主题和会话 id 类
 */
@RedisHash("Session")
public class SessionSubscribe {
    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 用户订阅的主题 id
     */
    @Indexed
    private final String simpSubscriptionId;
    public String getSimpSubscriptionId() {
        return simpSubscriptionId;
    }
    /**
     * 订阅该主题的用户会话列表
     */
    private List<SimpSession> simpSessionList = new ArrayList<>();

    /**
     * 增加主题中的用户会话
     * @param sessionId 会话 id
     */
    public void addSimpSession(SimpSession sessionId) {
        simpSessionList.add(sessionId);
    }

    /**
     * 移除主题中的用户会话
     * @param sessionId 会话 id
     */
    public void removeSimpSession(SimpSession sessionId) {
        simpSessionList.remove(sessionId);
    }
    /**
     * 构造函数
     * @param simpSubscriptionId 订阅的主题 id
     */
    public SessionSubscribe(String simpSubscriptionId) {
        this.simpSubscriptionId = simpSubscriptionId;
    }
}
