package com.project.demo.service;


import com.project.demo.model.SysPermissionInit;
import com.project.demo.universal.Service;

import java.util.List;

/**
* @Description: SysPermissionInitService接口
* @author 张瑶
* @date 2018/05/18 14:37
*/
public interface SysPermissionInitService extends Service<SysPermissionInit> {

    List<SysPermissionInit> selectAllOrderBySort();

}