package com.project.demo.controller;

import com.alibaba.fastjson.JSON;
import com.project.demo.Respone.RespResult;
import com.project.demo.Respone.RetResponse;
import com.project.demo.aop.AnnotationLog;
import com.project.demo.model.UserInfo;
import com.project.demo.service.RedisService;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("redis")
public class RedisController extends BaseController{


    @Resource
    private RedisService redisService;

    @PostMapping("/setRedis")
  //  @AnnotationLog(remark = "redis")
    public RespResult<UserInfo> setRedis(String name) {
        UserInfo info=new UserInfo();
        info.setId("100");
        info.setUserName(name);
        String string = JSON.toJSONString(info);
        redisService.set("us",string);
        redisService.expire("us",10);
        //redisService.del("us");
        return RetResponse.makeOKRsp(info);
    }

    @GetMapping("/getRedis")
    public RespResult<UserInfo> getRedis() {
        String name = redisService.get("us");
        JSONObject object = JSONObject.fromObject(name);
        UserInfo bean = (UserInfo) JSONObject.toBean(object, UserInfo.class);
        return RetResponse.makeOKRsp(bean);

    }

}
