package com.project.demo.service;

import com.project.demo.model.RolePerm;
import com.project.demo.universal.Service;

import java.util.List;
import java.util.Set;

/**
* @Description: RolePermService接口
* @author 张瑶
* @date 2018/05/26 00:35
*/
public interface RolePermService extends Service<RolePerm> {

    List<String> getPermsByUserId(String userId);

}