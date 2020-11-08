package com.xgoding.springboot.websocket.controller;

import cn.hutool.core.lang.Dict;
import com.xgoding.springboot.websocket.model.ServerInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务器监控 Controller
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.controller
 * @description:
 * @author: yxguang
 * @date: 2020/11/5
 * @version: V1.0
 * @modified: yxguang
 */
@RestController
@RequestMapping
public class ServerController {

    //获取监控信息
    @GetMapping("/server")
    public Dict serverInfo(HttpSession session) {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.copyInfo();
        String id = session.getId();
        System.out.println("HttpSession ID:" + id);
        return Dict.create().set("server",serverInfo);
    }

}
