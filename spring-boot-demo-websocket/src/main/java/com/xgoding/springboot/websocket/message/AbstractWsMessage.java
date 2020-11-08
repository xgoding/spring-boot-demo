package com.xgoding.springboot.websocket.message;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.web.socket.WebSocketMessage;

import java.io.Serializable;

/**
 * <p>
 * 消息类
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.message
 * @description: 消息类
 * @author: yxguang
 * @date: 2020/11/6
 * @version: V1.0
 * @modified: yxguang
 */
public abstract class AbstractWsMessage implements Serializable {
    private String time;
    private String content;
    private String type;

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    protected AbstractWsMessage(String content) {
        this.time = DateUtil.now();
        this.content = content;
        this.type = getMessageType();
    }

    /**
     * 获取消息类型
     * @return
     */
    protected abstract String getMessageType();

    /**
     * 转换为JSON字符串
     * @return
     */
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public abstract WebSocketMessage toTextMessage();
}
