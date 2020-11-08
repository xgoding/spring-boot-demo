package com.xgoding.springboot.websocket.handler;

import com.xgoding.springboot.websocket.message.TextWsMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;

import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  WebSocket 处理器 直接实现WebSocketHandler 接口
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.handler
 * @description: WebSocket 处理器
 * @author: yxguang
 * @date: 2020/11/6
 * @version: V1.0
 * @modified: yxguang
 */
public class MyWebSocketHandler implements WebSocketHandler {


    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //连接建立
        System.out.println("WebSocket connect established >>>>>");
        //输出Session信息
        printSession(session);
    }

    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //接收到消息
        printMessage(message);
        Object payload = message.getPayload();
        String reqMsg = payload.toString();
        TextWsMessage vo = TextWsMessage.create( "Response from "+this.getClass().getSimpleName()+": " + reqMsg);
        session.sendMessage(vo.toTextMessage());
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
       //传输错误
        printError(exception);
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //连接断开
        printClose(session,closeStatus);
    }

    public boolean supportsPartialMessages() {
        //对于大消息体，是否支持分段传输
        return false;
    }

    /**
     * 打印Session信息
     * @param session
     */
    private void printSession(WebSocketSession session) {
        String id = session.getId();
        boolean open = session.isOpen();
        String acceptedProtocol = session.getAcceptedProtocol();
        int binaryMessageSizeLimit = session.getBinaryMessageSizeLimit();
        Map<String, Object> attributes = session.getAttributes();
        URI uri = session.getUri();
        List<WebSocketExtension> extensions = session.getExtensions();
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        InetSocketAddress localAddress = session.getLocalAddress();
        Principal principal = session.getPrincipal();
        InetSocketAddress remoteAddress = session.getRemoteAddress();
        int textMessageSizeLimit = session.getTextMessageSizeLimit();
        StringBuilder sessionInfo = new StringBuilder();
        sessionInfo
                .append("SessionId: ").append(id).append("\n")
                .append("IsOpen: ").append(open).append("\n")
                .append("AcceptedProtocol: ").append(acceptedProtocol).append("\n")
                .append("BinaryMessageSizeLimit: ").append(binaryMessageSizeLimit).append("\n")
                .append("TextMessageSizeLimit: ").append(textMessageSizeLimit).append("\n")
                .append("Uri: ").append(uri != null ? uri.toString() : null).append("\n")
                .append("HandshakeHeaders: ").append(handshakeHeaders.toString()).append("\n")
                .append("LocalAddress: ").append(localAddress).append("\n")
                .append("RemoteAddress: ").append(remoteAddress).append("\n")
                .append("Principal: ").append(principal != null ? principal.getName() : null).append("\n")
                .append("Session Attributes: ").append("\n");
        attributes.forEach((k,v)->{
            sessionInfo.append("\t").append(k).append(": ").append(v).append("\n");
        });
        sessionInfo.append("WebSocket Extensions: ").append("\n");
        extensions.forEach(a->{
            sessionInfo.append("\tName: ").append(a.getName()).append("\n");
        });
        System.out.println(sessionInfo);
    }

    /**
     * Print message info
     * @param message
     */
    private void printMessage(WebSocketMessage<?> message) {
        String className = message.getClass().getName();
        System.out.println("Message received >>>>>>");
        System.out.println("Message Class: " + className);
        Object payload = message.getPayload();
        System.out.println("Message PayLoad: " + payload);
        System.out.println("Message PayLoad Length: "+message.getPayloadLength());
    }

    /**
     * Print throwable info.
     * @param throwable
     */
    private void printError(Throwable throwable) {
        System.out.println("Transport Exception:");
        throwable.printStackTrace();
    }

    /**
     * Print close status info.
     * @param session
     * @param closeStatus
     */
    private void printClose(WebSocketSession session, CloseStatus closeStatus) {
        System.out.println("Session Closed: "+session.getId());
        System.out.println("CloseStatus Code: "+closeStatus.getCode());
        System.out.println("CloseStatus Reason: " +closeStatus.getReason());
    }
}
