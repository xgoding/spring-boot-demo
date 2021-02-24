package com.xgoding.mq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
public class MqClient {
    //生产消息
    public void produce(String message) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream());
        ){
            out.println(message);
            out.flush();
        }
    }

    //消费消息
    public String consume() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            out.println("CONSUME");
            out.flush();
            return in.readLine();
        }
    }
}
