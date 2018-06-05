package com.project.demo.queue;

import com.project.demo.model.SystemLog;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@Component
public class SystemLogQueue {
    //创建日志的存放队列 减少程序反应时间

    private BlockingDeque<SystemLog> blockingDeque=new LinkedBlockingDeque<>();

    public void add(SystemLog systemLog) {
      blockingDeque.add(systemLog);
    }

    public SystemLog poll() throws InterruptedException {
        return blockingDeque.poll(1, TimeUnit.SECONDS);
    }
  }
