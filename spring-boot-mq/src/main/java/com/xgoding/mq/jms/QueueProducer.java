package com.xgoding.mq.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.memory.buffer.MessageQueue;

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
public class QueueProducer {
    public static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    public static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        //创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKER_URL);
        try {
            //创建连接
            Connection connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建会话
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建队列
            Queue queue = session.createQueue("active-mq-jms-1");
            //消息生产者
            MessageProducer producer = session.createProducer(queue);
            //创建消息对象
            TextMessage message = session.createTextMessage("测试消息");
            //发送一条消息
            producer.send(message);
            //提交事务
            session.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
