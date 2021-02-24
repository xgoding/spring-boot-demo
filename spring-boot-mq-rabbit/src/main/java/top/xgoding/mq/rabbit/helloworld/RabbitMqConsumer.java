package top.xgoding.mq.rabbit.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.mq.rabbit
 * @description:
 * @author: yxguang
 * @date: 2021/2/24
 * @version: V1.0
 * @modified: yxguang
 */
public class RabbitMqConsumer {
    private static final String USERNAME = ConnectionFactory.DEFAULT_USER;
    public static final String PASSWORD = ConnectionFactory.DEFAULT_PASS;
    public static final String HOST = ConnectionFactory.DEFAULT_HOST;
    public static final String VHOST = ConnectionFactory.DEFAULT_VHOST;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);

        connectionFactory.setHost(HOST);
        connectionFactory.setVirtualHost(VHOST);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "hello-exchange";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);

        //声明队列
        String queueName = channel.queueDeclare().getQueue();
        String routingKey = "testRoutingKey";
        //绑定队列，通过路由键将队列和交换器绑定
        channel.queueBind(queueName, exchangeName, routingKey);

        while (true){
            //消费消息
            boolean autoAck = false;
            String consumerTag = "";
            channel.basicConsume(queueName, autoAck, consumerTag, new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    System.out.println("消费路由键：" + routingKey);
                    System.out.println("消费内容类型：" + contentType);
                    long deliveryTag = envelope.getDeliveryTag();
                    //确认消息
                    channel.basicAck(deliveryTag, false);
                    System.out.println("消费内容：" + new String(body, StandardCharsets.UTF_8));
                }
            });
        }
    }
}
