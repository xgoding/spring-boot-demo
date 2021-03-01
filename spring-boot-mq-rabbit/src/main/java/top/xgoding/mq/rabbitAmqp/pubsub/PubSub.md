# Publish/Subscribe 模型
一次向多个消费者发送消息。

## Exchange 交换器

生产者并不直接将消息发送到消息队列，而是发送到交换器中，生产者甚至并不知道消息发送到哪儿个队列。

交换器，用于从生产者接收消息，并将消息发送到队列中。

交换器类型：  
1. direct 
2. topic
3. headers
4. fanout

## Temporary queues (临时队列)

通过 `queueDecare()` 创建  non-durable, exclusive, autodelete 的队列，队列名称随机创建。
通过 `queueBind(queueName,exchangeName,"")` 将 exchange 与 队列进行绑定。

