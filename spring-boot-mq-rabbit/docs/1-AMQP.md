# AMQP简介

## AMQP 协议

AMQP（Advanced message queue protocol）高级消息队列协议，基于消息队列的生产/消费模型。
版本分格式为major-minor[-revision],目前主要版本为 AMQP0-9-1(Rabbit) 、AMQP0-10(ActiveMQ、Apollo、Qpid)

## 基本概念

1. Message(消息)
   用于存储数据的二进制最小单元，格式包括：内容头、属性和内容体。
2. Publisher(消息生产者)
   向交换器发布消息的客户端。
3. Exchange(交换器)
   用于接收生产者生产的消息，并将消息路由到消息服务器中的队列。
4. Binding(绑定)
   用于描述消息队列和交换器之间的关联。
5. Virtual Host(虚拟主机)
   代表一个独立的消息服务器，其中包含自己的队列、交换器、绑定和权限机制。
6. Broker(消息代理)
   表示消息队列服务器实体，接受客户端连接，实现AMQP消息队列和路由功能的过程。消息代理可包含多个虚拟主机。
7. RoutingKey(路由规则)
   根据路由规则VirtualHost确定如何将消息路由到队列。
8. Queue(队列)
   用于保存消息，作为消息的容器，等待消费者消费消息。
9. Connection(连接)
   客户端与消息服务器的一个TCP连接。
10. Channel(信道)
   独立的双向数据流，用于降低TCP连接建立消耗的资源。一个连接可以有多个信道。
   
