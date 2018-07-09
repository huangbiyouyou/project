package com.project.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.demo.Respone.RespResult;
import com.project.demo.Respone.RetResponse;
import com.project.demo.aop.AnnotationLog;
import com.project.demo.exception.ServiceException;
import com.project.demo.model.UserInfo;
import com.project.demo.service.UserInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/UserInfo")
public class UserInfoController {

    @Resource
    UserInfoService userInfoService;
//localhost:8080/swagger-ui.html
//http://localhost:8080/druid/login.html



    @ApiOperation(value = "查询用户", notes = "根据用户ID查询用户")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Id", value = "用户ID", required = true,
                    dataType = "String", paramType = "query")
    )
    @GetMapping("/selectById")
    @AnnotationLog(remark = "查询")
    public RespResult<UserInfo> getUserInfo(@RequestParam("Id")String Id){
        UserInfo userInfo = userInfoService.selectById(Id);
        return RetResponse.makeOKRsp(userInfo);
    }


    @GetMapping("/hello")
    public String hello(){
        return "hello SpringBoot";
    }

    @GetMapping("/testException")
    public RespResult<UserInfo> testException(String id){
        List a = null;
        a.size();
        UserInfo userInfo = userInfoService.selectById(id);
        return RetResponse.makeOKRsp(userInfo);
    }

    @ApiOperation(value = "查询用户", notes = "分页查询用户所有")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码",
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数",
                    dataType = "Integer", paramType = "query")
    })
    @GetMapping("/selectAll")
    public RespResult<PageInfo<UserInfo>> getAllUserInfo(@RequestParam(defaultValue = "0")Integer page,
                                                         @RequestParam(defaultValue = "0")Integer size){
        PageHelper.startPage(page,size);
        List<UserInfo> userInfos = userInfoService.selectAll();
        PageInfo<UserInfo>pageInfo=new PageInfo<>(userInfos);
        return RetResponse.makeOKRsp(pageInfo);
    }


    @GetMapping("/selectAlla")
    public RespResult<PageInfo<UserInfo>> getAllaUserInfo(@RequestParam(defaultValue = "0")Integer page,
                                                         @RequestParam(defaultValue = "0")Integer size){
        PageHelper.startPage(page,size);
        List<UserInfo> userInfos = userInfoService.selectAll();
        PageInfo<UserInfo>pageInfo=new PageInfo<>(userInfos);
        return RetResponse.makeOKRsp(pageInfo);
    }



    @PostMapping("/login")
    public RespResult<UserInfo> login(String userName, String password) {
        Subject currentUser = SecurityUtils.getSubject();

        //登录
        try {
            currentUser.login(new UsernamePasswordToken(userName, password));
        }catch (IncorrectCredentialsException i){
            throw new ServiceException("密码输入错误");
        }

        UserInfo userInfo= (UserInfo) currentUser.getPrincipal();
        return RetResponse.makeOKRsp(userInfo);
    }
}
