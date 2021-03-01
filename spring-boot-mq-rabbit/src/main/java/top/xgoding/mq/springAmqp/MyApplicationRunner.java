package top.xgoding.mq.springAmqp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    private Publisher publisher;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            publisher.sendMsg("Hello spring amqp");
            //publisher.sendMsg2("Hello spring amqp");
            //publisher.sendMsg3("Hello spring amqp");
            //publisher.sendMsg4("Hello spring amqp");
            Thread.sleep(10000);
        }
    }
}
