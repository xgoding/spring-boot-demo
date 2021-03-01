package top.xgoding.mq.springAmqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 同步接收数据
 * </p>
 *
 * @package: top.xgoding.mq.springAmqp
 * @description:
 * @author: yxguang
 * @date: 2021/2/28
 * @version: V1.0
 * @modified: yxguang
 */
@Component
@Slf4j
public class SyncReceiver {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void receive() {
        //默认采用rabbitTemplate配置的queue
        final Message message = rabbitTemplate.receive();
        log.info("[x] Received : {}", new String(message.getBody()));
    }

    public void receive2() {
        //指定消息队列
        final Message message = rabbitTemplate.receive("default-queue");
        log.info("[x] Received : {}", new String(message.getBody()));
    }

    public void receive3() {
        //指定超时时间
        final Message message = rabbitTemplate.receive(1000);
        log.info("[x] Received : {}", new String(message.getBody()));
    }

    public void receive4() {
        //指定超时时间
        final Message message = rabbitTemplate.receive("default-queue",1000);
        log.info("[x] Received : {}", new String(message.getBody()));
    }

    public void receive5() {
        //接收消息并转换为对象
        final Object o = rabbitTemplate.receiveAndConvert();
    }

    public void receiveAndReply() {
        //接收并回复消息
        rabbitTemplate.receiveAndReply(new ReceiveAndReplyCallback<Object, Object>() {
            @Override
            public Object handle(Object payload) {
                return "payload-reply";
            }
        });
    }
}
