package com.xgoding.springboot.websocket.config;

import com.xgoding.springboot.websocket.handler.MyHandShakeHandler;
import com.xgoding.springboot.websocket.interceptor.MyHttpSessionHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * <p>
 * STOMP 支持
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.config
 * @description: WebSocket + SocketJS + STOMP 实现
 * @author: yxguang
 * @date: 2020/11/9
 * @version: V1.0
 * @modified: yxguang
 */
@Configuration
//开始消息代理支持
@EnableWebSocketMessageBroker
public class StockJsStompConfig implements WebSocketMessageBrokerConfigurer {

    //注册端点
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                //设置连接端点，HTTP URL
                .addEndpoint("/socketJsStomp")
                .addInterceptors(handshakeInterceptor())
                .setHandshakeHandler(handshakeHandler())
                .withSockJS();
    }

    //定义握手拦截器
    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new MyHttpSessionHandshakeInterceptor();
    }
    //定义握手处理器
    @Bean
    public HandshakeHandler handshakeHandler() {
        return new MyHandShakeHandler(new TomcatRequestUpgradeStrategy());
    }

    //配置消息代理
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //设置目的地前缀，通过/app/* 的url将会被路由到 @controller 注解下的 @MessageMapping 方法
        registry.setApplicationDestinationPrefixes("/coding","/gaming");
        //内置消息代理，消息目的地前缀，'/topic' ，'/queue' 没有实际意义，仅用于区分订阅模型。不同消息代理可能不同。
        registry.enableSimpleBroker("/topic", "/queue","/xgoding");
    }
}
