package top.xgoding.springboot.logging.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.springboot.logging.controller
 * @description:
 * @author: yxguang
 * @date: 2021/3/2
 * @version: V1.0
 * @modified: yxguang
 */
@RequestMapping("/log")
@RestController
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/debug")
    public void debug() {
        logger.debug("debug");
    }

    @GetMapping("/info")
    public void info() {
        logger.info("info");
    }

    @GetMapping("/error")
    public void error() {
        logger.error("error");
    }
}
