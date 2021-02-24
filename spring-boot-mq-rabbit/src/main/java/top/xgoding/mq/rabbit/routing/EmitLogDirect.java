package top.xgoding.mq.rabbit.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Random;
import java.util.UUID;

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
public class EmitLogDirect {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionFactory.DEFAULT_HOST);
        try(
                final Connection connection = factory.newConnection();
                final Channel channel = connection.createChannel();
        ){
            //定义direct类型交换器
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            //发送消息
            while (true) {
                String severity = getSeverity();
                String message = UUID.randomUUID().toString();
                channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("utf-8"));
                System.out.println("[*] Sent '" + severity + "' : '" + message + "'");
                Thread.sleep(1000);
            }
        }
    }

    private static String getSeverity() {
        Random random = new Random();
        final int i = random.nextInt(4) + 1;
        switch (i) {
            case 1:
                return "info";
            case 2:
                return "warn";
            case 3:
                return "debug";
            case 4:
                return "error";
            default:
                return "info";
        }
    }
}
