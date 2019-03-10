package com.garylee.rpc.dto;

import java.io.Serializable;

/**
 * 封装rpc响应
 * Created by GaryLee on 2019-03-10 17:50.
 */
public class RpcResponse implements Serializable{
    public static int SUCCESS_CODE = 0;
    public static int FAIL_CODE = 1;

    private int code;//状态码
    private String message;//信息
    private Object data;//数据

    private RpcResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static RpcResponse success() {
        return new RpcResponse(SUCCESS_CODE,null,null);
    }
    public static RpcResponse success(Object data) {
        return new RpcResponse(SUCCESS_CODE,"",data);
    }
    public static RpcResponse fail(String message) {
        return new RpcResponse(FAIL_CODE,message,null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
