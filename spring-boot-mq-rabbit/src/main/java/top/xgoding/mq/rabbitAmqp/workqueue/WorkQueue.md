# 工作队列
## 应用场景
工作队列主要用于解决执行资源密集型的任务，同时需要等待任务长时间执行完成的操作。
如在HTTP请求中执行耗时操作，可以先将耗时操作放入工作队列，再进行请求响应，避免请求超时。

## 消息轮询
消息生产者向队列发送消息。多个消息消费者通过轮询方式消费队列中的消息。

## 消息确认
RabbitMQ 支持消费者向生产者发送消息确认通知，确保消息正常消费后，RabbitMQ删除消息。

## 消息持久化
当RabbitMQ 退出时，可能会造成未持久化的消息丢失。如果确保消息不会丢失，需要确保以下步骤：
1. 确保消息队列在RabbitMQ重启后恢复。
2. 确保消息队列名称唯一，RabbitMQ 不允许重新定义已经存在队列的参数，如果队列已经存在，且不是持久化的，则需要重新定义队列。

## 合理分发消息
RabbitMQ 通过Round-Robin 轮询派发消息时，不关心当前接收消息的Worker是否空闲。
如果有两个Worker，一个一直接收耗时操作，一个一直接收瞬时操作，RabbitMQ会一直延续这种方式。
为了解决这个问题，我们可以设置Qos参数为1，即RabbitMQ不会给没有返回ACK消息确认的worker发送消息。