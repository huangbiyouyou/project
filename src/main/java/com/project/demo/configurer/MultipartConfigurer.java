package com.project.demo.configurer;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MultipartConfigurer {

    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory=new MultipartConfigFactory();
        factory.setMaxFileSize("10MB");
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }
}
