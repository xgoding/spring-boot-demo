package com.xgoding.springboot.websocket.handler;

import com.xgoding.springboot.websocket.message.PongWsMessage;
import com.xgoding.springboot.websocket.message.TextWsMessage;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * <p>
 * 文本消息 处理器
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.handler
 * @description: 文本消息处理器
 * @author: yxguang
 * @date: 2020/11/6
 * @version: V1.0
 * @modified: yxguang
 */
public class MyTextWebSocketHandler extends TextWebSocketHandler {
    //TextWebSocketHandler 不支持 binary 类型消息
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        //处理文本消息
        TextWsMessage vo = TextWsMessage.create( "Response from "+MyTextWebSocketHandler.class.getSimpleName()+": " + message.getPayload());
        session.sendMessage(vo.toTextMessage());
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
        //接收到Ping消息，返回pong消息
        //一个ping 或者 pong 都只是一个常规的帧， 只是这个帧是一个控制帧。Ping消息的opcode字段值为 0x9，pong消息的opcode值为  0xA 。
        //详见：[控制帧章节](https://tools.ietf.org/html/rfc6455#page-37)
        //TODO 客户端Ping消息实现？
        //向客户端发送Pong消息
        session.sendMessage(PongWsMessage.create().toTextMessage());
    }
}
