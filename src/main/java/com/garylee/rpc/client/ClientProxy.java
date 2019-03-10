package com.garylee.rpc.client;

import com.garylee.rpc.dto.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 客户端代理
 * Created by GaryLee on 2019-03-10 16:53.
 */
public class ClientProxy implements InvocationHandler{

    /** 
    * 生成代理对象
    * @param clazz 委托类(一般为服务,如UserService)
    * @return:代理对象
    */
    @SuppressWarnings("unchecked")
    public <T>T newProxyInstance(Class clazz){
//        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz},this);
    }

    /** 
    * 代理对象调用
    * @param proxy 代理对象
    * @param method 需要调用的方法
    * @param args 参数(object数组)
    * @return:
    * @Date: 2019/03/10 16:59
    */ 
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        Object result = method.invoke(proxy,args);
        //上面是本地代理本地的，而实际上rpc是远程过程调用，所以要交给网络协议执行
        //封装好request
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParamsTypes(method.getParameterTypes());
        request.setParams(args);

        //交给bio(socket)处理
        RpcClient client = new RpcClient();
        Object result = client.send(request);
        return result;
    }
}
