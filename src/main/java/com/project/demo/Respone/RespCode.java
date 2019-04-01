package com.project.demo.Respone;

public enum RespCode {

    SUCCESS(200),

    FAIL(400),

    // 未认证（签名错误）
    UNAUTHORIZED(401),

    // 接口不存在
    NOT_FOUND(404),

    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500),

    /** 未登录 */
    UNAUTHEN(4401),

    /** 未登录 */
    UNKNOWUSER(300),

    /** 未授权，拒绝访问 */
    UNAUTHZ(4403);

    public int code;

    RespCode(int code) {
        this.code=code;
    }
}
