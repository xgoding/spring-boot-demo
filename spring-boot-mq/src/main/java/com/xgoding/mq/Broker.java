package com.xgoding.mq;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * <p>
 *  消息处理中心
 * </p>
 *
 * @package: com.xgoding.mq
 * @description:
 * @author: yxguang
 * @date: 2021/2/23
 * @version: V1.0
 * @modified: yxguang
 */
public class Broker {
    //消息最大数量
    private static final int MAX_SIZE = 3;

    //消息存储容器
    private static ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<>(MAX_SIZE);

    //生产消息
    public static void produce(String msg) {
        if (messageQueue.offer(msg)) {
            System.out.println("成功向消息中心投递消息：" + msg + "，当前暂存的消息数量是：" + messageQueue.size());
        }else{
            System.out.println("消息中心内暂存的消息达到最大负荷，不能继续放入消息!");
        }
        System.out.println("=========================");
    }

    //消费消息
    public static String consume() {
        String msg = messageQueue.poll();
        if (msg != null) {
            System.out.println("已消费消息：" + msg + "，当前暂存的消息数量是：" + messageQueue.size());
        }else{
            System.out.println("消息处理中心内没有消息可供消费！");
        }
        System.out.println("=========================");
        return msg;
    }


}
