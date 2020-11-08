package com.xgoding.springboot.websocket.decorator;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

/**
 * <p>
 *  WebSocketHandler装饰器
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.decorator
 * @description: 通过装饰者模式，增加额外功能，如日志，异常捕获等。
 * @author: yxguang
 * @date: 2020/11/7
 * @version: V1.0
 * @modified: yxguang
 */
public class MyWebSocketHandlerDecorator extends WebSocketHandlerDecorator {
    public MyWebSocketHandlerDecorator(WebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        System.out.println("MyWebSocketHandlerDecorator handleTransportError >>>>>>");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        System.out.println("MyWebSocketHandlerDecorator handleMessage >>>>>>");
    }
}
