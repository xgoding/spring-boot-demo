package com.xgoding.springboot.websocket.message;

/**
 * <p>
 * 消息类型
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.message
 * @description: 消息类型
 * @author: yxguang
 * @date: 2020/11/6
 * @version: V1.0
 * @modified: yxguang
 */
public interface MessageType {
    /**
     * 文本
     */
    String TEXT = "text";
    /**
     * 二进制
     */
    String BINARY = "binary";
    /**
     * 心跳
     */
    String PONG = "pong";
}
