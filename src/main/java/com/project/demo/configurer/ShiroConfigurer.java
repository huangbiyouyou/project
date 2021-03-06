package com.project.demo.configurer;

import com.project.demo.model.SysPermissionInit;
import com.project.demo.service.SysPermissionInitService;
import com.project.demo.shiro.CustomRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class ShiroConfigurer {


    @Resource
    private SysPermissionInitService sysPermissionInitService;

    @Bean
    public Realm realm(){
        return new CustomRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        //缓存管理
       // securityManager.setCacheManager(jedisCacheManager());
        //会话管理
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        //可以设置shiro提供的会话管理机制
        //defaultWebSessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return defaultWebSessionManager;
    }

//    @Bean
//    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
//        /**
//         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
//         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
//         * 加入这项配置能解决这个bug
//         */
//        creator.setUsePrefix(true);
//        return creator;
//    }


    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){

        DefaultShiroFilterChainDefinition chain=new DefaultShiroFilterChainDefinition();

       // chain.addPathDefinition( "/UserInfo/selectById", "authc, roles[admin]");
        chain.addPathDefinition( "/logout", "anon");
        chain.addPathDefinition( "/UserInfo/hello", "anon");
        chain.addPathDefinition( "/UserInfo/selectAll", "anon");
        chain.addPathDefinition( "/UserInfo/login", "anon");
        chain.addPathDefinition( "/UserInfo/selectById", "anon");
        chain.addPathDefinition( "/redis/setRedis", "anon");
        chain.addPathDefinition( "/redis/getRedis", "anon");
        chain.addPathDefinition( "/**", "authc");

//        List<SysPermissionInit> list = sysPermissionInitService.selectAllOrderBySort();
//        for(int i = 0,length = list.size();i<length;i++){
//            SysPermissionInit sysPermissionInit = list.get(i);
//            chain.addPathDefinition(sysPermissionInit.getUrl(), sysPermissionInit.getPermissionInit());
//        }

        return chain;
    }

}
