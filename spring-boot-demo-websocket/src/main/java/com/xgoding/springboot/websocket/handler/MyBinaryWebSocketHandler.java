package com.xgoding.springboot.websocket.handler;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

/**
 * <p>
 * 二进制消息处理
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.handler
 * @description: @link{BinaryWebSocketHandler} 用于实现二进制消息处理
 * @author: yxguang
 * @date: 2020/11/6
 * @version: V1.0
 * @modified: yxguang
 */
public class MyBinaryWebSocketHandler extends BinaryWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        super.handleTextMessage(session, message);
        //不支持
    }

    /**
     * 处理ping/pong消息，详见{@link MyTextWebSocketHandler#handlePongMessage(WebSocketSession, PongMessage)}
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
        //接收Ping消息

    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        super.handleBinaryMessage(session, message);
        //接收二进制消息
        System.out.println(message.getPayload());
    }
}
