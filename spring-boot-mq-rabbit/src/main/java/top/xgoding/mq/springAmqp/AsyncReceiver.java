package top.xgoding.mq.springAmqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.mq.springAmqp
 * @description:
 * @author: yxguang
 * @date: 2021/3/1
 * @version: V1.0
 * @modified: yxguang
 */
@Component
@Slf4j
public class AsyncReceiver {

    public void handleMessage(String message) {
        log.info("[X] Async receiver receive : {}", message);
    }

    @RabbitListener(queues = {"default-queue","default-queue-2"})
    public void processMessage(String message) {
        log.info("[x] Async receiver process:{}", message);
    }
}
