package com.xgoding.mq;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @package: com.xgoding.mq
 * @description:
 * @author: yxguang
 * @date: 2021/2/23
 * @version: V1.0
 * @modified: yxguang
 */
public class ConsumeClient {
    public static void main(String[] args) throws IOException {
        MqClient client = new MqClient();
        String consume = client.consume();
        System.out.println("获取到消息：" + consume);
    }
}
