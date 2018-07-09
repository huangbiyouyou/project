package com.project.demo.dao;


import com.project.demo.model.SysPermissionInit;
import com.project.demo.universal.Mapper;

import java.util.List;

public interface SysPermissionInitMapper extends Mapper<SysPermissionInit> {

    List<SysPermissionInit> selectAllOrderBySort();
}