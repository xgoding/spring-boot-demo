package top.xgoding.mq.rabbit.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Random;
import java.util.StringJoiner;
import java.util.UUID;

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
public class EmitLogTopic {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionFactory.DEFAULT_HOST);

        try (final Connection connection = factory.newConnection();
             final Channel channel = connection.createChannel()){

            //定义exchange
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            while (true) {
                //发送消息
                String routingKey = getRouting();
                String message = UUID.randomUUID().toString();
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("utf-8"));
                System.out.println("[*] Sent '" + routingKey + "' : '" + message + "'");
                Thread.sleep(1000);
            }
        }
    }

    private static String getRouting() {
        Random random = new Random();
        int i = random.nextInt(4) + 1;
        String severity;
        switch (i) {
            case 1:
                severity =  "info";
                break;
            case 2:
                severity =  "warn";
                break;
            case 3:
                severity =  "debug";
                break;
            case 4:
                severity= "error";
                break;
            default:
                severity= "info";
        }
        String word;
        i = random.nextInt(4) + 1;
        switch (i) {
            case 1:
                word =  "kernel";
                break;
            case 2:
                word =  "cron";
                break;
            case 3:
                word =  "auth";
                break;
            case 4:
                word= "opt";
                break;
            default:
                word= "kernel";
        }
        final StringJoiner stringJoiner = new StringJoiner(".");
        stringJoiner.add(word);
        stringJoiner.add(severity);
        return stringJoiner.toString();
    }

}
