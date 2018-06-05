package com.project.demo.configurer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.project.demo.Respone.RespCode;
import com.project.demo.Respone.RespResult;
import com.project.demo.exception.ServiceException;
import com.project.demo.interceptor.Interceptor1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebConfigurer extends WebMvcConfigurationSupport {

   private Logger logger= LoggerFactory.getLogger(WebConfigurer.class);

    private static final String IZATION = "CHUCHEN";
   // 消息转换器处理
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter4 converter4=new FastJsonHttpMessageConverter4();
        converter4.setSupportedMediaTypes(getSupportedMediaTypes());

        FastJsonConfig config=new FastJsonConfig();

        config.setSerializerFeatures(

                //SerializerFeature.WriteNullListAsEmpty
                // String null -> ""
                SerializerFeature.WriteNullStringAsEmpty,
                // Number null -> 0
                SerializerFeature.WriteNullNumberAsZero,
                //禁止循环引用
                SerializerFeature.DisableCircularReferenceDetect
        );
        converter4.setFastJsonConfig(config);
        converter4.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter4);
    }

    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        return supportedMediaTypes;
    }


    @Override
    protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(getHandlerExceptionResolver());
    }

    private HandlerExceptionResolver getHandlerExceptionResolver() {
        HandlerExceptionResolver handlerExceptionResolver=new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
                RespResult<Object> result = getResuleByHeandleException(httpServletRequest, o, e);
                responseResult(httpServletResponse, result);
                return new ModelAndView();

            }
        };
        return handlerExceptionResolver;
    }

    private void responseResult(HttpServletResponse response, RespResult<Object> result) {

        response.setHeader("Access-Control-Allow-Origin","*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);

        try {
            response.getWriter().write(JSON.toJSONString(result,SerializerFeature.WriteMapNullValue));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private RespResult<Object> getResuleByHeandleException(HttpServletRequest request, Object o, Exception e) {
        RespResult<Object> result=new RespResult<>();

        if (e instanceof ServiceException) {
            result.setCode(RespCode.FAIL).setMsg(e.getMessage()).setData(null);
            return result;
        }

        if (e instanceof NoHandlerFoundException) {
            result.setCode(RespCode.NOT_FOUND).setMsg("接口 [" + request.getRequestURI() + "] 不存在");
            return result;
        }
        result.setCode(RespCode.INTERNAL_SERVER_ERROR).setMsg("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
        String message;

        if (o instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) o;
            message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s", request.getRequestURI(),
                    handlerMethod.getBean().getClass().getName(), handlerMethod.getMethod() .getName(), e.getMessage());
        } else {
            message=e.getMessage();
        }

        logger.error(message,e);
        return result;
    }

    //继承WebMvcConfigurationSupport之后，静态文件映射会出现问题，需要重新指定静态资源
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/META-INF/resources/favicon.ico");
                super.addResourceHandlers(registry);
    }


    //  请求拦截器

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor1() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String ization = request.getHeader("ization");
                if (IZATION.equals(ization)) {
                    return true;
                }else {
                    RespResult<Object> result = new RespResult<>();
                    result.setCode(RespCode.UNAUTHORIZED).setMsg("签名认证失败");
                    responseResult(response, result);
                    return false;
                }
            }//这里添加的是拦截的路径  /**为全部拦截
        }).addPathPatterns("/UserInfo/selectAlla");
    }
}
