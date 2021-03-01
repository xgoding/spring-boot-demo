package top.xgoding.mq.rabbitAmqp.pubsub;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
public class ReceiveLogs {
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
            //定义匿名队列
            final String queueName = channel.queueDeclare().getQueue();
            //绑定交换器和队列
            channel.queueBind(queueName, EXCHANGE_NAME, "");

            System.out.println("[*] Waiting for message. ");
            DeliverCallback callback = new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery delivery) throws IOException {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println("[x] Received message : " + message);
                }
            };
            //消费消息
            channel.basicConsume(queueName, true, callback,consumerTag -> {});
            System.in.read();
        }
    }
}
