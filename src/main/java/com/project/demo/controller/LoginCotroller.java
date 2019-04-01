package com.project.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LoginCotroller extends BaseController{

    @RequestMapping("")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect("");
    }
}
