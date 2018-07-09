package com.project.demo.service.impl;


import com.project.demo.dao.SysPermMapper;
import com.project.demo.model.SysPerm;
import com.project.demo.service.SysPermService;
import com.project.demo.universal.AbsratctService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @Description: SysPermService接口实现类
* @author 张瑶
* @date 2018/05/26 00:30
*/
@Service
public class SysPermServiceImpl extends AbsratctService<SysPerm> implements SysPermService {

    @Resource
    private SysPermMapper sysPermMapper;

}