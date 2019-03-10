package com.garylee.rpc.client;

import com.garylee.rpc.dto.RpcRequest;
import com.garylee.rpc.dto.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * Created by GaryLee on 2019-03-10 17:19.
 */
public class RpcClient {
    private String host = "127.0.0.1";
    private int port = 6789;
    public Object send(RpcRequest request){
        try {
            Socket socket = new Socket(host,port);
            //建立io流
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //发送请求
            oos.writeObject(request);
            oos.flush();
            //接收返回
            Object object = ois.readObject();
            RpcResponse response = (RpcResponse) object;
            if(response.getCode()==0)
                return response.getData();//成功返回数据
            else
                return response.getMessage();//失败返回错误信息
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
