package com.garylee.rpc.test;

import com.garylee.rpc.server.RpcServer;

/**
 * Created by GaryLee on 2019-03-10 21:42.
 */
public class Server {
    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.receive();
    }
}
