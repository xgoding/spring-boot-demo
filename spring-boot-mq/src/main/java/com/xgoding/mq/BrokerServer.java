package com.xgoding.mq;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 *  消息中心服务
 * </p>
 *
 * @package: com.xgoding.mq
 * @description:
 * @author: yxguang
 * @date: 2021/2/23
 * @version: V1.0
 * @modified: yxguang
 */
public class BrokerServer implements Runnable{
    //服务端口
    public static final int SERVICE_PORT = 9999;

    private final Socket socket;

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
        ){
           while (true){
               String str = in.readLine();
               if (str == null) {
                   continue;
               }
               System.out.println("接收到原始数据：" + str);
               if (str.equalsIgnoreCase("CONSUME")) {
                   String msg = Broker.consume();
                   out.println(msg);
                   out.flush();
               }else {
                   Broker.produce(str);
               }
           }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(SERVICE_PORT);
        while (true) {
            BrokerServer server = new BrokerServer(socket.accept());
            new Thread(server).start();
        }
    }
}
