# Routing 路由
选择性接收消息。

## Bindings 绑定关系
Bindings 是 exchange 与 queue 之间的关系，可以简单的认为 queue 对 exchange 中的消息感兴趣。

Bindings 还有一个额外的参数 routingKey，即 binding key. 如下所示：
`channel.queueBind(queueName,exchangeName,routingKey)`  
fanout 类型的exchange会自动忽略 routingKey.

## Direct 类型 exchange
Direct exchange 将消息RoutingKey 与 绑定关系中 BindingKey 一致匹配的消息发送到对应Queue中。
未匹配的消息则直接丢弃。

## Multiple bindings

