package com.garylee.rpc.service;

import com.garylee.rpc.annotation.Reference;

/**
 * Created by GaryLee on 2019-03-10 21:39.
 */
@Reference(UserService.class)
public class UserServiceImpl implements UserService{
    @Override
    public String getUserInfo(User user) {
        String userInfo = "用户姓名:"+user.getName()+"\t用户年龄"+user.getAge();
        return userInfo;
    }
}