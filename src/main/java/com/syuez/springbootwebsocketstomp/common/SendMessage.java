package com.syuez.springbootwebsocketstomp.common;

public interface SendMessage {
    /**
     * 开始发送
     */
    void start();

    /**
     * 停止发送
     */
    void stop();
}
