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
import java.util.concurrent.*;

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
            //加入线程池
            Executor threadPool = new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
            while (true){
                Socket socket = ss.accept();
                //给线程池消费
                RpcTask task = new RpcTask(socket,services);
                threadPool.execute(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
