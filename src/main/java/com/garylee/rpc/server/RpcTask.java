package com.garylee.rpc.server;

import com.garylee.rpc.dto.RpcRequest;
import com.garylee.rpc.dto.RpcResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * Created by GaryLee on 2019-03-16 22:59.
 */
public class RpcTask implements Runnable{
    private Socket socket;
    private Map<String,Object> services;

    public RpcTask(Socket socket, Map<String, Object> services) {
        this.socket = socket;
        this.services = services;
    }

    @Override
    public void run() {
        try {
            //创建io流
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //获取request对象
            Object object = ois.readObject();
            RpcRequest request = (RpcRequest) object;
            //开始执行
            Object service = services.get(request.getClassName());
            Class<?> clazz = service.getClass();
            //用clazz根据方法名和参数类型确定method
            Method method = clazz.getMethod(request.getMethodName(), request.getParamsTypes());
            //调用方法(client调用代理对象间接调用server)
            Object result = method.invoke(service, request.getParams());
            //封装好response返回
            RpcResponse response = RpcResponse.success(result);
            oos.writeObject(response);
            oos.flush();
        }catch (Exception e){

        }
    }
}
