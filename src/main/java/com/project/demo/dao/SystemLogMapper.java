package com.project.demo.dao;

import com.project.demo.model.SystemLog;
import com.project.demo.universal.Mapper;

import java.util.List;

public interface SystemLogMapper extends Mapper<SystemLog> {
    Integer insertByBatch(List<SystemLog> list);
}