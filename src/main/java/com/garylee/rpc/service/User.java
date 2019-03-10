package com.garylee.rpc.service;

import java.io.Serializable;

/**
 * Created by GaryLee on 2019-03-10 21:37.
 */
public class User implements Serializable{
    private String name;
    private int age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
