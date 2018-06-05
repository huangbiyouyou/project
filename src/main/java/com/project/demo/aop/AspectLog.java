package com.project.demo.aop;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonPointer;
import com.project.demo.exception.ServiceException;
import com.project.demo.model.SystemLog;
import com.project.demo.model.UserInfo;
import com.project.demo.queue.SystemLogQueue;
import com.project.demo.service.SystemLogService;
import com.project.demo.utils.ApplicationUtils;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.ibatis.javassist.compiler.JvstCodeGen.clazzName;

@Aspect
@Component
public class AspectLog {

    private static final Logger logger = LoggerFactory.getLogger(AspectLog.class);

    @Resource
    private SystemLogQueue systemLogQueue;

    @Pointcut("@annotation(com.project.demo.aop.AnnotationLog)")
    public void methodCachePointcut(){

    }
    @Before("methodCachePointcut()")
    public void doBefore(JoinPoint point)throws Exception{
        SystemLog systemLog = getSystemLogInit(point);
        systemLog.setLogType(SystemLog.LOGINFO);
        logger.info(systemLog.toString());
        systemLogQueue.add(systemLog);
    }

    @AfterThrowing(pointcut = "methodCachePointcut()",throwing = "e")
    public void doAfterThrowing(JoinPoint point,Throwable e)throws Throwable{
        //业务异常不用记录
        if(!(e instanceof ServiceException)) {
            try {
            SystemLog systemLog =getSystemLogInit(point);
            systemLog.setLogType(SystemLog.LOGERROR);
            systemLog.setExceptionCode(getClass().getName());
            systemLog.setExceptionDetail(e.getMessage());
                systemLogQueue.add(systemLog);
            } catch (Exception ex) {
                logger.error("==异常通知异常==");
                logger.error("异常信息:{}", ex.getMessage());
            }
        }
    }

    private SystemLog getSystemLogInit(JoinPoint point) {
        SystemLog systemLog = new SystemLog();
        try {
            String targetClass = point.getTarget().getClass().toString();
            //类名
            String className = point.getTarget().getClass().getName();
            String methodName = point.getSignature().getName();

            Class<?> aClass = Class.forName(className);
            String name = aClass.getName();
            //请求参数名+参数值的map
            Map<String, Object> nameAndArgs = getFiledName(this.getClass(), className, methodName, point.getArgs());
            systemLog.setId(ApplicationUtils.getUUID());
            systemLog.setDescription(getMthodRemark(point));
            systemLog.setMethod(targetClass+"."+methodName);
            //大家可自行百度获取ip的方法
            systemLog.setRequestIp("192.168.1.104");
            systemLog.setParams(JSON.toJSONString(nameAndArgs));
            systemLog.setUserId(getUserId());
            systemLog.setCreateTime(new Date());

        }catch (Exception ex){
            logger.error("==异常通知异常==");
            logger.error("异常信息:{}", ex.getMessage());
        }
        return systemLog;
    }

    private String getMthodRemark(JoinPoint point) throws Exception{
        String targetName = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] arguments = point.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String methode = "";
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    AnnotationLog methodCache = m.getAnnotation(AnnotationLog.class);
                    if (methodCache != null) {
                        methode = methodCache.remark();
                    }
                    break;
                }
            }
        }
        return methode;
    }

    private String getUserId() {
        String userId = "2";
//        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
//        if(userInfo != null){
//            userId = userInfo.getId();
//        }
        return userId;
    }

    private Map<String, Object> getFiledName(Class aClass, String className, String methodName, Object[] args)throws NotFoundException {
        Map<String, Object> map = new HashMap<>();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(aClass);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(className);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            //HttpServletRequest 和HttpServletResponse 不做处理
            if(!(args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse)){
                //paramNames即参数名
                map.put(attr.variableName(i + pos), JSON.toJSONString(args[i]));
            }
        }
        return map;
    }

}
