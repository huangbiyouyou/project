package com.project.demo.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.demo.Respone.RespResult;
import com.project.demo.Respone.RetResponse;
import com.project.demo.model.UserRole;
import com.project.demo.service.UserRoleService;
import com.project.demo.utils.ApplicationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* @Description: UserRoleController类
* @author 张瑶
* @date 2018/05/25 23:27
*/
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    @PostMapping("/insert")
    public RespResult<Integer> insert(UserRole userRole) throws Exception{
		userRole.setId(ApplicationUtils.getUUID());
    	Integer state = userRoleService.insert(userRole);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/deleteById")
    public RespResult<Integer> deleteById(@RequestParam String id) throws Exception {
        Integer state = userRoleService.deleteById(id);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/update")
    public RespResult<Integer> update(UserRole userRole) throws Exception {
        Integer state = userRoleService.update(userRole);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/selectById")
    public RespResult<UserRole> selectById(@RequestParam String id) throws Exception {
        UserRole userRole = userRoleService.selectById(id);
        return RetResponse.makeOKRsp(userRole);
    }

    /**
	* @Description: 分页查询
	* @param page 页码
	* @param size 每页条数
	* @Reutrn RetResult<PageInfo<UserRole>>
	*/
    @PostMapping("/list")
    public RespResult<PageInfo<UserRole>> list(@RequestParam(defaultValue = "0") Integer page,
					@RequestParam(defaultValue = "0") Integer size) throws Exception {
        PageHelper.startPage(page, size);
        List<UserRole> list = userRoleService.selectAll();
        PageInfo<UserRole> pageInfo = new PageInfo<UserRole>(list);
        return RetResponse.makeOKRsp(pageInfo);
    }
}