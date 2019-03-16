package com.garylee.rpc.service;

import com.garylee.rpc.annotation.Reference;
import com.garylee.rpc.annotation.Service;

/**
 * Created by GaryLee on 2019-03-10 21:39.
 */
@Service(UserService.class)
public class UserServiceImpl implements UserService{
    @Override
    public String getUserInfo(User user) {
        String userInfo = "用户姓名:"+user.getName()+"\t用户年龄"+user.getAge();
        System.out.println("查询用户:"+user.getName());
        return userInfo;
    }
}
