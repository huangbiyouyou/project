package com.project.demo.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

//@Component
public class ActiveMQServer {

    @JmsListener(destination = "you")
    public void receive(String message) {
        System.out.println("收到的 message 是：" + message);
    }

}
