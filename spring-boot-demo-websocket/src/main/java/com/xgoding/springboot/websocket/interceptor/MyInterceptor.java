package com.xgoding.springboot.websocket.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;


/**
 * <p>
 * 自定义的握手拦截器，通过继承{@link DefaultHandshakeHandler} 类实现
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.interceptor
 * @description: 主要实现 {@link DefaultHandshakeHandler}
 * @author: yxguang
 * @date: 2020/11/7
 * @version: V1.0
 * @modified: yxguang
 */
public class MyInterceptor extends DefaultHandshakeHandler {

}
