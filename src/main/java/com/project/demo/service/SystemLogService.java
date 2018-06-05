package com.project.demo.service;

import com.project.demo.model.SystemLog;
import com.project.demo.universal.Service;

import java.util.List;

/**
* @Description: SystemLogService接口
*/
public interface SystemLogService extends Service<SystemLog> {

    Integer insertByBatch(List<SystemLog> list);

}