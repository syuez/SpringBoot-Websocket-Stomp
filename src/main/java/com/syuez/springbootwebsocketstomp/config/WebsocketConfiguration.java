package com.syuez.springbootwebsocketstomp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {
    /**
     * 心跳包时间
     */
    private static final long HEART_BEAT = 10000;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        /*
        * 定义心跳包线程
        * https://blog.csdn.net/cw_hello1/article/details/80885171
        */
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("wss-heartbeat-thread-");
        taskScheduler.initialize();
        /*
         * broker: 消息队列管理者，也可以成为消息代理。
         * 它有自己的地址（例如“/topic”），客户端可以向其发送订阅指令，它会记录哪些订阅了这个目的地址(destination)。
         * 应用自己可以定义以/topic打头的为发布订阅模式，消息会被所有消费者客户端收到。
         * 以/user开头的为点对点模式，只会被一个消费者客户端收到。
         */
        config.enableSimpleBroker("/topic").setHeartbeatValue(new long[] {HEART_BEAT,HEART_BEAT}).setTaskScheduler(taskScheduler);
        /*
         *  应用目的地址("/app"): 发送到这类目的地址的消息在到达broker之前，会先路由到由应用写的某个方法。
         *  相当于对进入broker的消息进行一次拦截，目的是针对消息做一些业务处理。
         * 而发送到 "/topic" 的消息会直接转到 broker，不会被应用拦截。
         */
        config.setApplicationDestinationPrefixes("/app");

        /*
         * 消息从生产者发出到消费者消费的流转流程
         * 首先，生产者通过发送一条SEND命令消息到某个目的地址(destination)
         * 服务端request channel接受到这条SEND命令消息
         * 如果目的地址是应用目的地址则转到相应的由应用自己写的业务方法做处理（对应图中的SimpAnnotationMethod），再转到broker(SimpleBroker)。
         * 如果目的地址是非应用目的地址则直接转到broker。
         * broker通过SEND命令消息来构建MESSAGE命令消息, 再通过response channel推送MESSAGE命令消息到所有订阅此目的地址的消费者。
         *
         * 来源：https://juejin.cn/post/6844903655477346317
         */

    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
         * SockJS 建立连接的地址
         */
        registry.addEndpoint("/stock-ticks").withSockJS();
    }
}
