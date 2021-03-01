package top.xgoding.mq.rabbitAmqp.springboot;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.mq.rabbit.springboot
 * @description:
 * @author: yxguang
 * @date: 2021/2/25
 * @version: V1.0
 * @modified: yxguang
 */
@Configuration
public class RabbitConfiguration {
    static final String EXCHANGE_NAME = "spring-boot-exchange";
    static final String QUEUE_NAME = "spring-boot-queue";

    /*@Bean
    public CachingConnectionFactory factory() {
        return new CachingConnectionFactory();
    }

    @Bean
    @ConditionalOnBean(CachingConnectionFactory.class)
    public RabbitAdmin admin(ConnectionFactory factory) {
        return new RabbitAdmin(factory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(factory());
    }*/

    //定义消息队列
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false, false, true);
    }

    //定义交换器
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME, false, true);
    }

    //定义绑定关系
    @Bean
    public Binding binding(Queue queue, TopicExchange  exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    //定义容器
    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory factory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void listener(String in) {
        System.out.println("received :" + in);
    }
    //定义消息监听
    @Bean
    public MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver,"receiveMessage");
    }
}
