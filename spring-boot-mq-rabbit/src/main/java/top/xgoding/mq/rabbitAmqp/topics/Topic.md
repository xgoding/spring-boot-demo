# Topic 模式
根据规则进行消息匹配。

## Topic Exchange.

Topic 模式的Exchange中的RouterKey不能随意定义，需要以单词进行拼接，以 "." 进行区分，最大不能超过255字节，如 "stock.usd.nyse" 。

匹配规则：
"*" 匹配 一个单词
"#" 匹配0个或多个单词

