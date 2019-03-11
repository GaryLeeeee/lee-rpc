package com.garylee.rpc.test;

import com.garylee.rpc.annotation.Reference;
import com.garylee.rpc.client.ClientProxy;
import com.garylee.rpc.client.RpcClient;
import com.garylee.rpc.server.RpcServer;
import com.garylee.rpc.service.*;

import java.io.File;
import java.lang.reflect.Field;

/**
 * 切记不能直接输出service
 * Created by GaryLee on 2019-03-10 21:42.
 */
public class Client {
    //暂时设置为static，这样所有调用都是用同一个，后面可考虑实现IOC
    @Reference
    public static CalculatorService calculatorService;
    @Reference
    public static UserService userService;

    public static void main(String[] args) {
        Client client = new Client();

        User user = new User();
        user.setName("teemo");
        user.setAge(12);
        System.out.println(userService.getUserInfo(user));

        Calculator calculator = new Calculator(30,50);
        System.out.println("计算结果为:"+calculatorService.add(calculator));
    }

    /**
     * 静态代码块加载，类加载时执行仅一次，主要是扫描test包里面注解有@Reference的字段，并引用上实例
     * 后续可移动到启动类(如springboot的Application)
     */
    static {
        try {
            //测试所在包
            File file = new File(System.getProperty("user.dir") + "\\src\\test\\java\\com\\garylee\\rpc\\test");
            //获取测试包里面所有类
            File[] files = file.listFiles();
            for (File f : files) {
                //判断是否为java文件
                if (f.getName().contains(".java")) {
                    Class clazz;

                    //根据路径获取className如com.garylee.rpc.test.Client
                    String fileName = "com.garylee.rpc.test." + f.getName().substring(0, f.getName().indexOf(".java"));
                    //获取当前Class(fileName为java sources root下面以"."为分隔符的路径
                    clazz = Class.forName(fileName);

                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        ClientProxy proxy = new ClientProxy();
                        if (field.getAnnotations()[0].annotationType() == Reference.class) {
                            Class c = Class.forName(field.getType().getName());
                            //给字段注入实例
                            field.set(clazz.newInstance(), proxy.newProxyInstance(c));
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
