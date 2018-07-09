package com.project.demo.service.impl;


import com.project.demo.dao.RolePermMapper;
import com.project.demo.model.RolePerm;
import com.project.demo.service.RolePermService;
import com.project.demo.universal.AbsratctService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @Description: RolePermService接口实现类
* @author 张瑶
* @date 2018/05/26 00:35
*/
@Service
public class RolePermServiceImpl extends AbsratctService<RolePerm> implements RolePermService {

    @Resource
    private RolePermMapper rolePermMapper;

    public List<String> getPermsByUserId(String userId){
        return rolePermMapper.getPermsByUserId(userId);
    }

}