package com.project.demo.dao;

import com.project.demo.model.UserInfo;
import com.project.demo.universal.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserInfoMapper extends Mapper<UserInfo>{

    /*UserInfo selectById(@Param("id") Integer id);

    List<UserInfo> selectAll();*/

}
