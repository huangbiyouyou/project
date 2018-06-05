package com.project.demo.startuprunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class StartupRunner1 implements ApplicationRunner {
     //项目启动的时候需要做一些初始化的操作
    private static final Logger logger = LoggerFactory.getLogger(StartupRunner1.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("服务器启动成功！<<<<使用ApplicationRunner接口");
    }
}
