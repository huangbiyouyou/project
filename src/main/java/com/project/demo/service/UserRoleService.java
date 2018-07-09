package com.project.demo.service;



import com.project.demo.model.UserRole;
import com.project.demo.universal.Service;

import java.util.List;

/**
* @Description: UserRoleService接口
* @author 张瑶
* @date 2018/05/25 23:27
*/
public interface UserRoleService extends Service<UserRole> {

    List<String> getRolesByUserId(String userId);

}