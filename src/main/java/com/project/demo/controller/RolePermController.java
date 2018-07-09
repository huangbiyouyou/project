package com.project.demo.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.demo.Respone.RespResult;
import com.project.demo.Respone.RetResponse;
import com.project.demo.model.RolePerm;
import com.project.demo.service.RolePermService;
import com.project.demo.utils.ApplicationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* @Description: RolePermController类
* @author 张瑶
* @date 2018/05/26 00:35
*/
@RestController
@RequestMapping("/rolePerm")
public class RolePermController {

    @Resource
    private RolePermService rolePermService;

    @PostMapping("/insert")
    public RespResult<Integer> insert(RolePerm rolePerm) throws Exception{
		rolePerm.setId(ApplicationUtils.getUUID());
    	Integer state = rolePermService.insert(rolePerm);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/deleteById")
    public RespResult<Integer> deleteById(@RequestParam String id) throws Exception {
        Integer state = rolePermService.deleteById(id);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/update")
    public RespResult<Integer> update(RolePerm rolePerm) throws Exception {
        Integer state = rolePermService.update(rolePerm);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/selectById")
    public RespResult<RolePerm> selectById(@RequestParam String id) throws Exception {
        RolePerm rolePerm = rolePermService.selectById(id);
        return RetResponse.makeOKRsp(rolePerm);
    }

    /**
	* @Description: 分页查询
	* @param page 页码
	* @param size 每页条数
	* @Reutrn RetResult<PageInfo<RolePerm>>
	*/
    @PostMapping("/list")
    public RespResult<PageInfo<RolePerm>> list(@RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "0") Integer size) throws Exception {
        PageHelper.startPage(page, size);
        List<RolePerm> list = rolePermService.selectAll();
        PageInfo<RolePerm> pageInfo = new PageInfo<RolePerm>(list);
        return RetResponse.makeOKRsp(pageInfo);
    }
}