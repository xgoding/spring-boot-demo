package com.xgoding.springboot.websocket.message;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

/**
 * <p>
 *  文本类型消息
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.message
 * @description: 文本类型消息
 * @author: yxguang
 * @date: 2020/11/6
 * @version: V1.0
 * @modified: yxguang
 */
public class TextWsMessage extends AbstractWsMessage {

    private TextWsMessage(String content) {
        super(content);
    }

    public static TextWsMessage create(String content) {
        return new TextWsMessage(content);
    }

    @Override
    protected String getMessageType() {
        return MessageType.TEXT;
    }

    @Override
    public WebSocketMessage toTextMessage() {
        return new TextMessage(super.toJSONString());
    }
}
