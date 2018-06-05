package com.project.demo.scheduld;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@EnableScheduling
public class Scheduld {

   /* 在26分、29分、33分执行一次：0 26,29,33 * * * ?

    每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?

    每隔5分钟执行一次：0 0/5 * * * ?*/

    @Scheduled(fixedDelay = 5000)
    public void job1(){
        System.out.println("定时任务1" + new Date());
    }


    @Scheduled(cron = "0 30 17 * * ?")
    public void job2(){
        System.out.println("定时任务2" + new Date());
    }
}
