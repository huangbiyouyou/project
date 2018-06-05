package com.project.demo.controller;

import com.project.demo.Respone.RespResult;
import com.project.demo.Respone.RetResponse;
import com.project.demo.constant.ExcelConstant;
import com.project.demo.model.ExcelData;
import com.project.demo.model.UserInfo;
import com.project.demo.service.UserInfoService;
import com.project.demo.utils.ExcelUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("excel")
public class ExcelController {

    @Resource
    private UserInfoService userInfoService;


    @PostMapping("/test")
    public RespResult<Integer> test(){
        int rowIndex = 0;
        List<UserInfo> list = userInfoService.selectAll();
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList();
        titles.add("ID");
        titles.add("userName");
        titles.add("password");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        for(int i = 0, length = list.size();i<length;i++){
            UserInfo userInfo = list.get(i);
            List<Object> row = new ArrayList();
            row.add(userInfo.getId());
            row.add(userInfo.getUser_name());
            row.add(userInfo.getAddress());
            rows.add(row);
        }
        data.setRows(rows);
        try{
            rowIndex = ExcelUtils.generateExcel(data, ExcelConstant.FILE_PATH + ExcelConstant.FILE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }
        return RetResponse.makeOKRsp(Integer.valueOf(rowIndex));
    }


    @GetMapping("/test2")
    public void test2(HttpServletResponse response){
        int rowIndex = 0;
        List<UserInfo> list = userInfoService.selectAll();
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList();
        titles.add("ID");
        titles.add("userName");
        titles.add("password");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        for(int i = 0, length = list.size();i<length;i++){
            UserInfo userInfo = list.get(i);
            List<Object> row = new ArrayList();
            row.add(userInfo.getId());
            row.add(userInfo.getUser_name());
            row.add(userInfo.getAddress());
            rows.add(row);
        }
        data.setRows(rows);
        try{
            ExcelUtils.exportExcel(response,"test2",data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
