package com.project.demo.shiro;

import com.project.demo.model.UserInfo;
import com.project.demo.service.RolePermService;
import com.project.demo.service.UserInfoService;
import com.project.demo.service.UserRoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm{


    @Autowired
    private UserInfoService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermService rolePermService;



    /**
     * 告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
     */
    {
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName("md5");
        hashMatcher.setStoredCredentialsHexEncoded(true);
        //加密的次数
        hashMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(hashMatcher);
    }

    /**
     *  定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfo userInfo = (UserInfo) getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setRoles(userInfo.getRoles());
        info.setStringPermissions(userInfo.getPerms());
        return info;
    }


    /**
     * 定义如何获取用户信息的业务逻辑，给shiro做登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken= (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        UserInfo info = userService.selectBy("userName", username);
        if (info == null) {
            throw new UnknownAccountException("No account found for admin [" + username + "]");
        }

        List<String> roleList = userRoleService.getRolesByUserId(info.getId());
        List<String> permList = rolePermService.getPermsByUserId(info.getId());
        Set<String> roles = new HashSet(roleList);
        Set<String> perms = new HashSet(permList);
        info.setRoles(roles);
        info.setPerms(perms);

        SimpleAuthenticationInfo info1=new SimpleAuthenticationInfo(info, info.getPassword(), getName());
        info1.setCredentialsSalt(ByteSource.Util.bytes(info.getSalt()));
        return info1;
    }
}
