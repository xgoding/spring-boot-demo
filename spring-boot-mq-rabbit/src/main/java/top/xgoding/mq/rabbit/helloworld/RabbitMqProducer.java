package top.xgoding.mq.rabbit.helloworld;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
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
public class RabbitMqProducer {
    private static final String USERNAME = ConnectionFactory.DEFAULT_USER;
    public static final String PASSWORD = ConnectionFactory.DEFAULT_PASS;
    public static final String HOST = ConnectionFactory.DEFAULT_HOST;
    public static final String VHOST = ConnectionFactory.DEFAULT_VHOST;

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        //设置地址
        connectionFactory.setHost(HOST);
        connectionFactory.setVirtualHost(VHOST);
        //建立代理服务器连接
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //声明交换器
        String exchangeName = "hello-exchange";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
        String routerKey = "testRoutingKey";

        //发布消息
        byte[] message = "quit".getBytes();
        channel.basicPublish(exchangeName, routerKey, null, message);

        //关闭信道、连接
        channel.close();
        connection.close();
    }
}
