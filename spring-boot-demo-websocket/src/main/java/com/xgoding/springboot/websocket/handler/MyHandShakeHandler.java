package com.xgoding.springboot.websocket.handler;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.support.AbstractHandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.ServletContext;

/**
 * <p>
 *  自定义握手处理器，参考 {@link DefaultHandshakeHandler}
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.handler
 * @description:自定义握手处理器
 * @author: yxguang
 * @date: 2020/11/10
 * @version: V1.0
 * @modified: yxguang
 */
public class MyHandShakeHandler extends DefaultHandshakeHandler{

    public MyHandShakeHandler() {
    }

    public MyHandShakeHandler(RequestUpgradeStrategy requestUpgradeStrategy) {
        super(requestUpgradeStrategy);
    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        super.setServletContext(servletContext);
        System.out.println("MyHandShakeHandler setServletContext()");
    }

    @Override
    public void start() {
        super.start();
        System.out.println("MyHandShakeHandler start()");
    }

    @Override
    public void stop() {
        super.stop();
        System.out.println("MyHandShakeHandler stop()");
    }
}
