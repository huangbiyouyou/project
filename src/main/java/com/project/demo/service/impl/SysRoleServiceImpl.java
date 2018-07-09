package com.project.demo.service.impl;

import com.project.demo.dao.SysRoleMapper;
import com.project.demo.model.SysRole;
import com.project.demo.service.SysRoleService;
import com.project.demo.universal.AbsratctService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @Description: SysRoleService接口实现类
* @author 张瑶
* @date 2018/05/25 23:01
*/
@Service
public class SysRoleServiceImpl extends AbsratctService<SysRole> implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

}