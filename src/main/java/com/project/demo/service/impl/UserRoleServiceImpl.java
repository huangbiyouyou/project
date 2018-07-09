package com.project.demo.service.impl;


import com.project.demo.dao.UserRoleMapper;
import com.project.demo.model.UserRole;
import com.project.demo.service.UserRoleService;
import com.project.demo.universal.AbsratctService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @Description: UserRoleService接口实现类
* @author 张瑶
* @date 2018/05/25 23:27
*/
@Service
public class UserRoleServiceImpl extends AbsratctService<UserRole> implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    public List<String> getRolesByUserId(String userId){
        return userRoleMapper.getRolesByUserId(userId);
    }

}