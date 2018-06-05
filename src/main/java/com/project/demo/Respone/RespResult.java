package com.project.demo.Respone;

public class RespResult<T> {

    public String getMsg() {
        return msg;
    }

    public RespResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public RespResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public RespResult<T> setCode(RespCode respCode) {
        this.code = respCode.code;
        return this;
    }

    public T getData() {
        return data;
    }

    public RespResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    private String msg;

    public int code;

    private T data;
}
