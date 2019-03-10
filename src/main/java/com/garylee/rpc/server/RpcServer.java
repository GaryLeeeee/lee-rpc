package com.garylee.rpc.server;

import com.garylee.rpc.dto.RpcRequest;
import com.garylee.rpc.dto.RpcResponse;
import com.garylee.rpc.utils.CommonUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 *
 * Created by GaryLee on 2019-03-10 18:54.
 */
public class RpcServer {
    private int port = 6789;//端口
    public void receive(){
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("6789端口监听ing");
            //将所有的服务初始化
            Map<String,Object> services = CommonUtils.initService();
            while (true){
                Socket socket = ss.accept();
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
                Method method = clazz.getMethod(request.getMethodName(),request.getParamsTypes());
                //调用方法(client调用代理对象间接调用server)
                Object result = method.invoke(service,request.getParams());
                //封装好response返回
                RpcResponse response = RpcResponse.success(result);
                oos.writeObject(response);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
