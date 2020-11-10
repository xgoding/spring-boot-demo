package com.xgoding.springboot.websocket.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;


/**
 * <p>
 * 自定义的握手拦截器，通过继承{@link HttpSessionHandshakeInterceptor} {}类实现
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.interceptor
 * @description: 主要实现 {@link HttpSessionHandshakeInterceptor}
 * @author: yxguang
 * @date: 2020/11/7
 * @version: V1.0
 * @modified: yxguang
 */
public class MyHttpSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("MyHttpSessionHandshakeInterceptor beforeHandshake()");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
        System.out.println("MyHttpSessionHandshakeInterceptor afterHandshake()");
    }
}
