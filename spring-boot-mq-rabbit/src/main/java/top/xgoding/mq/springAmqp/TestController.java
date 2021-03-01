package top.xgoding.mq.springAmqp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/amqp")
@RestController
public class TestController {
    @Autowired
    private SyncReceiver receiver;

    @GetMapping("/receive")
    public void receive() {
        receiver.receive();
    }

    @GetMapping("/reply")
    public void reply() {
        receiver.receiveAndReply();
    }
}
