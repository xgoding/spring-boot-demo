package top.xgoding.mq.springAmqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.mq.springAmqp
 * @description:
 * @author: yxguang
 * @date: 2021/2/26
 * @version: V1.0
 * @modified: yxguang
 */
@Component
@Slf4j
public class Publisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //默认exchange routingKey 发送消息
    public void sendMsg(String msg) throws UnsupportedEncodingException {
        MessageProperties properties = new MessageProperties();
        //properties 不能为空
        Message message = new Message(msg.getBytes(StandardCharsets.UTF_8),properties);
        rabbitTemplate.send(message);
        log.info("[*] Sent to default exchange with default routingKey: {}", msg);
    }

    //默认exchange 自定义routingKey 发送消息
    public void sendMsg2(String msg) {
        MessageProperties properties = new MessageProperties();
        Message message = new Message(msg.getBytes(StandardCharsets.UTF_8),properties);
        rabbitTemplate.send("custom-routing-key", message);
        log.info("[*] Sent to default exchange with custom routingKey : {}", msg);
    }

    //自定义exchange 自定义routingKey 发送消息
    public void sendMsg3(String msg) throws UnsupportedEncodingException {
        MessageProperties properties = new MessageProperties();
        //Message message = new Message(msg.getBytes(StandardCharsets.UTF_8),properties);
        Message message = MessageBuilder.withBody(msg.getBytes("UTF-8"))
                .setMessageId(UUID.randomUUID().toString())
                .setHeader("bar", "baz")
                .build();
        rabbitTemplate.send("custom-direct-exchange","custom-routing-key", message);
        log.info("[*] Sent to custom exchange with custom routingKey : {}", msg);
    }

    //发送correlateData
    public void sendMsg4(String msg) throws UnsupportedEncodingException {
        String correlationId = UUID.randomUUID().toString();
        final Message sendMessage = MessageBuilder.withBody(msg.getBytes("UTF-8"))
                .setMessageId(UUID.randomUUID().toString())
                .setCorrelationId(correlationId)
                .build();
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(correlationId);
        final Message returnMessage = MessageBuilder.withBody("return-message".getBytes())
                .build();
        correlationData.setReturnedMessage(returnMessage);
        rabbitTemplate.send("", "", sendMessage, correlationData);
        log.info("[*] Sent to default exchange with default routingKey with correlate data : {}", msg);
    }
}
