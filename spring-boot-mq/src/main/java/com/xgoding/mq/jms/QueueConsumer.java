package com.xgoding.mq.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * <p>
 *
 * </p>
 *
 * @package: com.xgoding.mq.jms
 * @description:
 * @author: yxguang
 * @date: 2021/2/23
 * @version: V1.0
 * @modified: yxguang
 */
public class QueueConsumer {
    public static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    public static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        try {
            //创建连接工厂
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
            //创建连接
            Connection connection = connectionFactory.createConnection();
            //开启连接
            connection.start();
            //创建会话
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建消息队列
            Queue queue = session.createQueue("active-mq-jms-1");
            //消息消费者
            MessageConsumer consumer = session.createConsumer(queue);
            //监听消息
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("收到消息："+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread.sleep(100 * 1000);
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
