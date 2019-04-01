package com.project.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.demo.Respone.RespResult;
import com.project.demo.Respone.RetResponse;
import com.project.demo.anno.SerializedField;
import com.project.demo.aop.AnnotationLog;
import com.project.demo.exception.ServiceException;
import com.project.demo.model.UserInfo;
import com.project.demo.service.RedisService;
import com.project.demo.service.UserInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/UserInfo")
public class UserInfoController extends BaseController{

    @Resource
    UserInfoService userInfoService;

    @Resource
    private RedisService redisService;

    /**
     * localhost:8080/swagger-ui.html
     * http://localhost:8080/druid/login.html
     */




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
        logger.info("接收到请求");
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
    @SerializedField(includes = {"data", "msg","code"}, encode = false)
    public RespResult<PageInfo<UserInfo>> getAllUserInfo(@RequestParam(defaultValue = "0",required = false)Integer page,
                                                         @RequestParam(defaultValue = "0",required = false)Integer size){
        logger.info("来了老弟");
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

    @RequestMapping("/index.do")
    public String toIndexPage() {
        logger.info("toIndexPage");
        return "main/index";
    }



    @PostMapping("/login")
    public RespResult<UserInfo> login(@RequestParam(value = "userName")String userName, @RequestParam(value = "password")String password, HttpServletRequest request, HttpServletResponse response) {
        Subject currentUser = SecurityUtils.getSubject();

        //登录
        try {
            currentUser.login(new UsernamePasswordToken(userName, password));
            currentUser.getSession().setTimeout(-1000L);
        } catch (IncorrectCredentialsException i) {
            throw new ServiceException("密码输入错误");
        }

        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        
        String sessionId = String.valueOf(currentUser.getSession().getId());
//        String sessionId2 = redisService.get(userName + "redis");
//        SessionKey sessionKey = new WebSessionKey(sessionId2, request, response);
//        try {
//            Session session = securityManager.getSession(sessionKey);
//            if (!sessionId.equals(sessionId2))
//                sessionManager.getSessionDAO().delete(session);
//                logger.info("踢出了"+userName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        long del = redisService.del(userName + "redis");
//        boolean set = redisService.set(userName + "redis", sessionId);

        UserInfo userInfo = (UserInfo) currentUser.getPrincipal();
        return RetResponse.makeOKRsp(userInfo);
    }

    @GetMapping("/loginout")
    public RespResult loginout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return RetResponse.makeOKRsp();
    }
}
