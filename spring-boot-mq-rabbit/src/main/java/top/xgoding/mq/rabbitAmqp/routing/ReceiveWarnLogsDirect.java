package top.xgoding.mq.rabbitAmqp.routing;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.mq.rabbit.routing
 * @description:
 * @author: yxguang
 * @date: 2021/2/24
 * @version: V1.0
 * @modified: yxguang
 */
public class ReceiveWarnLogsDirect {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionFactory.DEFAULT_HOST);
        try(
                final Connection connection = factory.newConnection();
                final Channel channel = connection.createChannel();
        ){
            //定义exchange
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            final String queueName = channel.queueDeclare().getQueue();

            //定义绑定关系
            channel.queueBind(queueName, EXCHANGE_NAME, "warn");

            System.out.println("[*] Waiting for messages.");

            DeliverCallback callback = new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery delivery) throws IOException {
                    String message = new String(delivery.getBody(), "utf-8");
                    final String routingKey = delivery.getEnvelope().getRoutingKey();
                    System.out.println("[x] Received '" + routingKey + "' : '" + message + "'");
                }
            };
            channel.basicConsume(queueName,true,callback,consumerTag -> {

            });
            System.in.read();
        }
    }
}
