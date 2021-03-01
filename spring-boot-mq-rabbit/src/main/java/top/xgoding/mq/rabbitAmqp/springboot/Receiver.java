package top.xgoding.mq.rabbitAmqp.springboot;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.mq.rabbit.spring
 * @description:
 * @author: yxguang
 * @date: 2021/2/25
 * @version: V1.0
 * @modified: yxguang
 */
@Component
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
