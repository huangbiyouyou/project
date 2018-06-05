package com.project.demo.service.impl;


import com.project.demo.dao.SystemLogMapper;
import com.project.demo.model.SystemLog;
import com.project.demo.service.SystemLogService;
import com.project.demo.universal.AbsratctService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @Description: SystemLogService接口实现类
*/
@Service
public class SystemLogServiceImpl extends AbsratctService<SystemLog> implements SystemLogService {

    @Resource
    private SystemLogMapper systemLogMapper;

    @Override
    public Integer insertByBatch(List<SystemLog> list) {
        return systemLogMapper.insertByBatch(list);
    }
}