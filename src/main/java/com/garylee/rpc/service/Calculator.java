package com.garylee.rpc.service;

import java.io.Serializable;

/**
 * Created by GaryLee on 2019-03-11 12:40.
 */
public class Calculator implements Serializable{
    int num1;
    int num2;

    public Calculator(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }
}
