package com.garylee.rpc.dto;

import java.io.Serializable;

/**
 * 封装rpc请求
 * Created by GaryLee on 2019-03-10 17:05.
 */
public class RpcRequest implements Serializable{
    private String className;//类名
    private String methodName;//方法名
    private Class<?>[] paramsTypes;//方法类型数组
    private Object[] params;//参数数组

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamsTypes() {
        return paramsTypes;
    }

    public void setParamsTypes(Class<?>[] paramsTypes) {
        this.paramsTypes = paramsTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
