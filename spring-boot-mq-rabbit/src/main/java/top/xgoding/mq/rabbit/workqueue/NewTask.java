package top.xgoding.mq.rabbit.workqueue;

import com.rabbitmq.client.*;

import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.mq.rabbit.workqueue
 * @description:
 * @author: yxguang
 * @date: 2021/2/24
 * @version: V1.0
 * @modified: yxguang
 */
public class NewTask {
    private static final String QUEUE_NAME = "TASK_QUEUE";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionFactory.DEFAULT_HOST);
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
        ) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            Scanner sc = new Scanner(System.in);
            while (true) {
                // final String s = sc.nextLine();
                // System.out.println("[x] Scanner input :" + s);
                UUID uuid = UUID.randomUUID();
                String message = String.join(" ", uuid.toString());
                channel.basicPublish("", QUEUE_NAME,MessageProperties.TEXT_PLAIN, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '"+message+"'");
                Thread.sleep(5*1000);
            }
        }
    }
}
