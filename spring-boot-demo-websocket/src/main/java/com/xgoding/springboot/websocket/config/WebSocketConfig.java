package com.xgoding.springboot.websocket.config;

import com.xgoding.springboot.websocket.decorator.MyWebSocketHandlerDecorator;
import com.xgoding.springboot.websocket.handler.MyBinaryWebSocketHandler;
import com.xgoding.springboot.websocket.handler.MyTextWebSocketHandler;
import com.xgoding.springboot.websocket.handler.MyWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * <p>
 * WebSocket 通用配置类
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.config
 * @description: 通用配置
 * @author: yxguang
 * @date: 2020/11/5
 * @version: V1.0
 * @modified: yxguang
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    //注册消息处理器
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                //###### 原生方式 ########
                //原生端点握手成功后，返回101状态码
                //添加泛型消息处理端点，不区分消息类型
                .addHandler(myWebSocketHandler(), "/myHandler")
                //添加文本消息处理端点
                .addHandler(myTextWebSocketHandler(), "/myTextHandler")
                //添加装饰过的文本消息处理端点
                .addHandler(myDecoratorWebSocketHandler(), "/myDecoratorTextHandler")
                //添加二进制消息处理端点
                .addHandler(myBinaryWebSocketHandler(), "/myBinaryHandler")
                //添加HttpSession拦截器，用于将HttpSession中的信息复制到WebSocketSession中
                //在实际应用中可用来区分登录用户
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                //设置允许的访问域名，主要有三种形式，详见[Allowed Origins](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-server-deployment)
                //1.（默认）仅支持同源请求。
                //2. (列表)在指定的请求源列表内，列表内的请求源必须以http://或者https://开头
                //3. (*)允许所有请求源
                .setAllowedOrigins("http://xgoding.top")
                //###### SocketJS FallBack ########
                //一：引入SocketJS原因：添加SockeJS 协议，通过fallback机制实现websocket相关API
                //在网络传输过程中，可能会因为服务器代理(如nginx)或者其他原因，导致服务器不制止Upgrade header 或 关闭空闲的长连接，导致Websocket不可用，通过SocketJS协议实现WebSocket机制。
                //二：实现方式主要包括三种：
                //1.尝试采用WebSocket协议
                //2.尝试采用HttpStreaming (Header 中xhr-streaming)
                //3.尝试采用长轮询(long polling)方式
                //通常先调用 /info 请求，获取服务器相关信息，决定采用那种传输策略。
                //传输请求通常采用 https://host:port/myApp/myEndpoint/{server-id}/{session-id}/{transport} 格式
                //三：IE8 IE9 浏览器支持
                //1.服务器是否需要传输Cookie
                //2.基于IFrame传输
                .withSockJS()
                //在IFrame （Response Header 中会增加 X-Frame-Options）中请求，默认SocketJS从CDN下载，建议通过同源策略下载（没太明白！！）
                .setClientLibraryUrl("http://localhost:8080/demo/js/sockjs.min.js")
                //Spring SocketJS 默认心跳时间为25秒，
                .setHeartbeatTime(1000)
                //Spring SocketJS 支持定时处理心跳任务
                .setTaskScheduler(mySocketJSHeartBeatSchedulerTask())
                //SocketJS 默认自动 添加CROS headers，此选项设置为true，不自动添加。
                .setSupressCors(false)
                //SocketJS 字节大小，默认128KB：128*1024
                .setStreamBytesLimit(5 * 1024)
                //SocketJS 设置http消息缓存大小，默认100
                .setHttpMessageCacheSize(10)
                //SocketJS 设置服务端延时断开链接时间，默认5秒，用于继续向客户端发送数据
                .setDisconnectDelay(1 * 1000);

    }

    /**
     * SocketJS heartsbeat scheduler task.
     * @return
     */
    @Bean
    public TaskScheduler mySocketJSHeartBeatSchedulerTask() {
        return new TaskScheduler() {
            @Override
            public ScheduledFuture<?> schedule(Runnable runnable, Trigger trigger) {
                System.out.println("schedule 1");
                return null;
            }

            @Override
            public ScheduledFuture<?> schedule(Runnable runnable, Date date) {
                System.out.println("schedule 2");
                return null;
            }

            @Override
            public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, Date date, long l) {
                System.out.println("schedule 3");
                return null;
            }

            @Override
            public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l) {
                System.out.println("schedule 4");
                return null;
            }

            @Override
            public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, Date date, long l) {
                System.out.println("schedule 5");
                return null;
            }

            @Override
            public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long l) {
                System.out.println("schedule 6");
                return null;
            }
        };

    }

    //定义泛型消息处理器
    @Bean
    public WebSocketHandler myWebSocketHandler() {
        return new MyWebSocketHandler();
    }

    //定义文本消息处理器
    @Bean
    public WebSocketHandler myTextWebSocketHandler() {
        return new MyTextWebSocketHandler();
    }
    //定义二进制消息处理器
    @Bean
    public WebSocketHandler myBinaryWebSocketHandler() {
        return new MyBinaryWebSocketHandler();
    }
    //定义带装饰者的消息处理器
    @Bean
    public WebSocketHandler myDecoratorWebSocketHandler() {
        return new MyWebSocketHandlerDecorator(new MyTextWebSocketHandler());
    }

    //定义Websocket 容器相关配置
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        //设置最大文本消息缓存大小
        container.setMaxTextMessageBufferSize(8192);
        //设置最大二进制消息缓存大小
        container.setMaxBinaryMessageBufferSize(8192);
        //设置会话空闲超时时间
        //container.setMaxSessionIdleTimeout(1000L);
        //设置消息发送超时时间
        //container.setAsyncSendTimeout(1000L);
        return container;
    }
}
