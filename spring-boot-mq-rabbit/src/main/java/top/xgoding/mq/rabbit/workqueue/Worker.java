package top.xgoding.mq.rabbit.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

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
public class Worker {
    private static final String QUEUE_NAME = "TASK_QUEUE";
    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        try (
                final Connection connection = factory.newConnection();
                final Channel channel = connection.createChannel();
        ){
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            channel.basicQos(1);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");

                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                } finally {
                    System.out.println(" [x] Done");
                    //手动确认消息
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
            System.in.read();
        }
    }

    private static void doWork(String task) {
        if (task.endsWith("a")) {
            try {
                System.out.println("task ["+task+"] is working.");
                Thread.sleep(10*1000);
            } catch (InterruptedException _ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
