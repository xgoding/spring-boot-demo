package com.xgoding.springboot.websocket.config;

import com.xgoding.springboot.websocket.decorator.MyWebSocketHandlerDecorator;
import com.xgoding.springboot.websocket.handler.MyBinaryWebSocketHandler;
import com.xgoding.springboot.websocket.handler.MyTextWebSocketHandler;
import com.xgoding.springboot.websocket.handler.MyWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * <p>
 * WebSocket 通用配置类
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.config
 * @description: 通用配置
 * @author: yxguang
 * @date: 2020/11/5
 * @version: V1.0
 * @modified: yxguang
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    //注册消息处理器
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                //添加泛型消息处理端点，不区分消息类型
                .addHandler(myWebSocketHandler(), "/myHandler")
                //添加文本消息处理端点
                .addHandler(myTextWebSocketHandler(), "/myTextHandler")
                //添加装饰过的文本消息处理端点
                .addHandler(myDecoratorWebSocketHandler(), "/myDecoratorTextHandler")
                //添加二进制消息处理端点
                .addHandler(myBinaryWebSocketHandler(), "/myBinaryHandler")
                //添加HttpSession拦截器，用于将HttpSession中的信息复制到WebSocketSession中
                //在实际应用中可用来区分登录用户
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                //设置允许的访问域名，主要有三种形式，详见[Allowed Origins](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-server-deployment)
                //1.（默认）仅支持同源请求。
                //2. (列表)在指定的请求源列表内，列表内的请求源必须以http://或者https://开头
                //3. (*)允许所有请求源
                .setAllowedOrigins("http://xgoding.top");
    }

    //定义泛型消息处理器
    @Bean
    public WebSocketHandler myWebSocketHandler() {
        return new MyWebSocketHandler();
    }

    //定义文本消息处理器
    @Bean
    public WebSocketHandler myTextWebSocketHandler() {
        return new MyTextWebSocketHandler();
    }
    //定义二进制消息处理器
    @Bean
    public WebSocketHandler myBinaryWebSocketHandler() {
        return new MyBinaryWebSocketHandler();
    }
    //定义带装饰者的消息处理器
    @Bean
    public WebSocketHandler myDecoratorWebSocketHandler() {
        return new MyWebSocketHandlerDecorator(new MyTextWebSocketHandler());
    }

    //定义Websocket 容器相关配置
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        //设置最大文本消息缓存大小
        container.setMaxTextMessageBufferSize(8192);
        //设置最大二进制消息缓存大小
        container.setMaxBinaryMessageBufferSize(8192);
        //设置会话空闲超时时间
        //container.setMaxSessionIdleTimeout(1000L);
        //设置消息发送超时时间
        //container.setAsyncSendTimeout(1000L);
        return container;
    }
}
