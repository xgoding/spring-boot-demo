# Spring AMQP

## 项目构成

Spring AMQP项目主要包括两个Module，spring-amqp和spring-rabbit.

1. spring-amqp  
spring-amqp 是 spring 对 AMQP0-9-1协议的抽象，并不涉及具体的实现。
具体协议实现在spring-rabbit包中，目前仅有者一个实现包。

2. spring-rabbit
spring-rabbit是对spring-amqp的具体实现。

## spring-amqp 核心类

| 类名称                                | 类说明                                     |
| :------------------------------------ | :------------------------------------------ |
| org.springframework.amqp.core.Message | 对消息的封装，主要包括消息属性、消息内容。 |
| org.springframework.amqp.core.Exchange | 对交换器的封装，每个交换器名称必须唯一。 |
| org.springframework.amqp.core.Queue | 对消息队列的封装。|
| org.springframework.amqp.core.Binding | 对exchange与queue绑定关系的封装。 |
| org.springframework.amqp.core.AmqpAdmin | 对amqp一系列便携性管理操作封装。 |
| org.springframework.amqp.core.AmqpTemplate | 对amqp常用操作，如发送消息、消费消息的封装。|

## spring-rabbit 核心类
| 类名称                                   | 类说明                                   |
| :-------------------------------------- | :-------------------------------------- |
| org.springframework.amqp.rabbit.connection.ConnectionFactory | 连接工厂，实现类负责完成对com.rabbitmq.client.Connection包装 |


## 连接工厂
spring-rabbit 提供了三类连接工厂的实现：
1. `org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory`
`PooledChannelConnectionFactory` 管理单个连接和两个ChannelPoll，一个事务信道，一个非事务信道。需要引入`common-pool2`jar包。
2. `org.springframework.amqp.rabbit.connection.ThreadChannelConnectionFactory`
`ThreadChannelConnetionFactory` 管理单个连接和两个ThreadLocal，一个用于事务处理，一个用于非事务处理。主要用于同一线程的操作使用相同的信道。
为了避免内存泄露，如果应用中使用短时间存活的线程，需要调用connectionFactory的 `closeThreadChannel()`来释放资源。
3. `org.springframework.amqp.rabbit.connection.CachingConnectionFactory`
该ConnectionFactory默认创建一个连接供代理中虚拟主机所共享，并且可以调用 `createChannel()` 创建信道。创建此工厂时，可以设置工厂的Host、username、
password，同时支持调用 `setChannelCacheSize` 设置信道缓冲大小，默认为25。

## AmqpTemplate

`org.springframework.amqp.core.AmqpTemplate` 是对常用操作的封装，主要包括消息发送、消息接收等相关操作，并不涉及具体接口实现。目前Spring中仅有
`org.springframework.amqp.rabbit.core.RabbitTemplate` 一个对应的实现类。

### 设置重试/恢复机制

RabbitTemplate 可以调用 `setRetryTemplate()` 设置重试机制。 通过配置 `retryTemplate` 策略，实现消息重发。
同时，可以通过 `retryTemplate` 的 `execute(retryCallback,recoveryCallback)` 设置恢复回调机制。

### 消息发送成功、失败监测
生产者发送消息是一个异步过程，默认情况下不能被路由的消息会被Broker直接丢弃。可以通过设置 `connectionListener` 监听连接的create、close、shutdown.








