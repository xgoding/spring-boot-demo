package top.xgoding.mq.rabbitAmqp.pubsub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.mq.rabbit.pubsub
 * @description:
 * @author: yxguang
 * @date: 2021/2/24
 * @version: V1.0
 * @modified: yxguang
 */
public class EmitLog {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionFactory.DEFAULT_HOST);

        try (
                final Connection connection = factory.newConnection();
                final Channel channel = connection.createChannel();
        ){
            //定义交换器
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            //发送消息
            while (true) {
                String message = String.join(" ", UUID.randomUUID().toString());
                channel.basicPublish(EXCHANGE_NAME, "",null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("[x] Sent '" + message + "'");
                Thread.sleep(1 * 1000);
            }
        }
    }
}
