package com.garylee.rpc.service;

import com.garylee.rpc.annotation.Service;

/**
 * Created by GaryLee on 2019-03-11 12:40.
 */
@Service(CalculatorService.class)
public class CalculatorServiceImpl implements CalculatorService{
    @Override
    public int add(Calculator calculator) {
        return calculator.num1 + calculator.num2;
    }
}
