package com.project.demo.controller;

import com.project.demo.Respone.RespResult;
import com.project.demo.Respone.RetResponse;
import com.project.demo.utils.UpdateFileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/uploadFile")
public class UploadFileController {

    @PostMapping("/upload")
    public RespResult<List<String>> upload(HttpServletRequest httpServletRequest) throws Exception {
        List<String> list = UpdateFileUtils.uploadFile(httpServletRequest);
        return RetResponse.makeOKRsp(list);
    }

}
