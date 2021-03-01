package top.xgoding.mq.springAmqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownSignalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.*;
import org.springframework.amqp.rabbit.core.CorrelationDataPostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.mq.springAmqp
 * @description:
 * @author: yxguang
 * @date: 2021/2/26
 * @version: V1.0
 * @modified: yxguang
 */
@Configuration
@Slf4j
public class RabbitMqConfig {

    //定义Connection命名策略
    @Bean
    public SimplePropertyValueConnectionNameStrategy cns() {
        return new SimplePropertyValueConnectionNameStrategy("spring.application.name");
    }

    //定义ConnectionFactory
    @Bean
    public CachingConnectionFactory cachingFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        //设置连接命名策略
        //factory.setConnectionNameStrategy(connectionFactory -> "spring-amqp-connection");
        //factory.setConnectionNameStrategy(cns());
        //设置生产者确认
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        factory.setPublisherReturns(true);
        //设置自定义客户端连接属性
        factory.getRabbitConnectionFactory().getClientProperties().put("version", "v1.0.0");
        //设置连接监听
        factory.addConnectionListener(new ConnectionListener() {
            @Override
            public void onCreate(Connection connection) {
                log.info("connection [{}] created.", connection.getDelegate().getClientProvidedName());
            }

            @Override
            public void onClose(Connection connection) {
                log.info("connection [{}] closed.", connection.getDelegate().getClientProvidedName());
            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                log.info("connection shutdown : {}", signal.getMessage());
            }
        });
        return factory;
    }

    //定义队列，作为默认
    @Bean
    public Queue defaultQueue() {
        return new Queue("default-queue", false, true, true);
    }

    //定义队列
    @Bean
    public Queue defaultQueue2() {
        return new Queue("default-queue-2", false, true, true);
    }

    //定义消息异步监听回调
    @Bean
    public MessageListener messageListener() {
        return new MessageListener() {
            @Override
            public void onMessage(Message message) {
                log.info("[x] Message listener receive : {}", new String(message.getBody()));
            }
        };
    }

    @Bean
    public MessageListener channelMessageListener() {
        return new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                log.info("[x] Channel: [{}] message listener receive {}", channel.getChannelNumber(), new String(message.getBody()));
            }
        };
    }

    //定义消息监听适配器，用于解耦MQ相关代码与业务POJO
    @Bean
    @ConditionalOnBean(AsyncReceiver.class)
    public MessageListenerAdapter messageListenerAdapter(AsyncReceiver receiver) {
        /*final MessageListenerAdapter ada = new MessageListenerAdapter();
        ada.setDelegate(receiver);
        ada.setDefaultListenerMethod("handleMessage");
        return ada;*/
        return new MessageListenerAdapter(receiver, "handleMessage");
    }

    //定义容器，用于建立队列与消息监听关系
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(MessageListenerAdapter adapter) {
        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(cachingFactory());
        container.setQueueNames("default-queue", "default-queue-2");
        container.setReceiveTimeout(1000);
        //设置监听回调，用于接收消息
        //container.setMessageListener(messageListener());
        //container.setMessageListener(channelMessageListener());
        //container.setMessageListener(adapter);
        return container;
    }

    //定义RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(cachingFactory());
        //设置重试机制
        RetryTemplate retryTemplate = new RetryTemplate();
        //简单重试机制
        //SimpleRetryPolicy backOffPolicy = new SimpleRetryPolicy();
        //backOffPolicy.setMaxAttempts(3);
        //指数重试机制
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        //设置消息退回监听
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("message returned #################");
//            log.info("message : {}", message);
//            log.info("replyCode : {}", replyCode);
//            log.info("replyText : {}", replyText);
//            log.info("exchange : {}", exchange);
//            log.info("routingKey : {}", routingKey);
        });
        //设置生产者消息确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("message confirm ##################");
//            log.info("correlationData : {}", correlationData);
//            log.info("ack : {}", ack);
//            log.info("cause : {}", cause);
        });
        //设置默认exchange，默认是空，direct类型交换器
        rabbitTemplate.setExchange("");
        //设置默认routingKey
        rabbitTemplate.setRoutingKey("default-queue");
        //设置默认队列
        rabbitTemplate.setDefaultReceiveQueue("default-queue");
        //设置接收超时，默认是0，立即返回null，小于0 无限期等待，大于0，超时返回异常
        rabbitTemplate.setReceiveTimeout(10 * 1000);
        //提交前修改Message的correlatedData
        rabbitTemplate.setCorrelationDataPostProcessor(new CorrelationDataPostProcessor() {
            @Override
            public CorrelationData postProcess(Message message, CorrelationData correlationData) {
                if (correlationData != null) {
                    correlationData.setId("111222");
                }
                 return correlationData;
            }
        });
        return rabbitTemplate;
    }

}
