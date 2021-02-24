package top.xgoding.mq.rabbit.topics;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.mq.rabbit.topics
 * @description:
 * @author: yxguang
 * @date: 2021/2/24
 * @version: V1.0
 * @modified: yxguang
 */
public class ReceiveKernLogsTopic {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionFactory.DEFAULT_HOST);
        try (
                final Connection connection = factory.newConnection();
                final Channel channel = connection.createChannel();
        ) {
            //定义exchange
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            //定义队列
            final String queueName = channel.queueDeclare().getQueue();

            //绑定
            String bindingKey = "kernel.#";
            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
            System.out.println("[x] Waiting for message.");
            DeliverCallback callback = new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery delivery) throws IOException {
                    String message = new String(delivery.getBody(), "utf-8");
                    String bindingKey = delivery.getEnvelope().getRoutingKey();
                    System.out.println("[x] Received '" + bindingKey + " : '" + message + "'");
                }
            };
            //消费
            channel.basicConsume(queueName,true,callback,consumerTag -> {

            });
            System.in.read();
        }
    }
}
