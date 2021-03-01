package top.xgoding.mq.springAmqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

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
@SpringBootApplication(scanBasePackages =  "top.xgoding.mq.springAmqp")
public class SpringAmqpBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(SpringAmqpBootstrap.class, args);
    }
}
