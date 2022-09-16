package com.syuez.springbootwebsocketstomp.repository;

import com.syuez.springbootwebsocketstomp.session.SessionSubscribe;
import org.springframework.data.repository.CrudRepository;

/**
 * 在 Redis 中操作 Session 数据的接口
 */
public interface SessionRedisRepository extends CrudRepository<SessionSubscribe, String> {
    /**
     * 通过 SimpSessionId 查找
     * @param sessionId SimpSessionId
     * @return SessionSubscribe
     */
    SessionSubscribe findBySimpSessionList_SimpSessionId(String sessionId);

    /**
     * 通过 SimpSubscriptionId 查找
     * @param subscriptionId SimpSubscriptionId
     * @return SessionSubscribe
     */
    SessionSubscribe findBySimpSubscriptionId(String subscriptionId);
}
