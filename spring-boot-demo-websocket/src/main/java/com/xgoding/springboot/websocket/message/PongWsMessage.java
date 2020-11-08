package com.xgoding.springboot.websocket.message;

import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.WebSocketMessage;

/**
 * <p>
 * 心跳消息
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.message
 * @description: 心跳消息
 * @author: yxguang
 * @date: 2020/11/6
 * @version: V1.0
 * @modified: yxguang
 */
public class PongWsMessage extends AbstractWsMessage{

    protected PongWsMessage(String content) {
        super(content);
    }

    @Override
    protected String getMessageType() {
        return MessageType.PONG;
    }

    @Override
    public WebSocketMessage toTextMessage() {
        return new PongMessage();
    }

    public static PongWsMessage create() {
        return new PongWsMessage(null);
    }
}
