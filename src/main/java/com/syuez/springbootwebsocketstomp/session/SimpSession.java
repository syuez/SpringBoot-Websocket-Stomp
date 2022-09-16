package com.syuez.springbootwebsocketstomp.session;

import lombok.Data;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 用户会话
 */
@Data
public class SimpSession {
    /**
     * 用户会话 id
     */
    @Indexed
    private String simpSessionId;
    public SimpSession(String simpSessionId) {
        this.simpSessionId = simpSessionId;
    }
}
