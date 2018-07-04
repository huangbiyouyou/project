package com.project.demo.constant;

public class ProjectConstant {

    // 项目基础包名称
    public static final String BASE_PACKAGE = "com.project.demo";

    // Model所在包
    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";

    // Mapper所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";

    // Service所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";

    // ServiceImpl所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";

    // Controller所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";

    // Mapper插件基础接口的完全限定名
    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".universal.Mapper";

    //文件上传储存的地址
    public static final String SAVEFILEPATH = "/Users/huangbiyou/Documents/office";


    public enum SignType {
        MD5, HMACSHA256
    }

    public static final String FIELD_SIGN = "sign";
    public static final String APIKEY = "";
    public static final String APPID = "";
    public static final String MCHID = "";

}
