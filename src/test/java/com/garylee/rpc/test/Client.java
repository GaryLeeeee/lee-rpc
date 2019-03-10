package com.garylee.rpc.test;

import com.garylee.rpc.client.ClientProxy;
import com.garylee.rpc.client.RpcClient;
import com.garylee.rpc.server.RpcServer;
import com.garylee.rpc.service.User;
import com.garylee.rpc.service.UserService;

/**
 * Created by GaryLee on 2019-03-10 21:42.
 */
public class Client {
    public static void main(String[] args) {
        ClientProxy proxy = new ClientProxy();
        UserService userService = proxy.newProxyInstance(UserService.class);
        User user = new User();
        user.setName("teemo");
        user.setAge(12);
        System.out.println(userService.getUserInfo(user));
    }
}
