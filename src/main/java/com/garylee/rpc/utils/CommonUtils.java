package com.garylee.rpc.utils;

import com.garylee.rpc.annotation.Reference;
import com.garylee.rpc.annotation.Service;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * 工具类
 * Created by GaryLee on 2019-03-10 19:03.
 */
public class CommonUtils {
    static String servicePackage = "com/garylee/rpc/service";//默认存放服务接口的包

    static String testPackage = "com\\garylee\\rpc\\test";//测试路径
    /**
     * 初始化代理对象
     */
    public static Map<String, Object> initService(){
        try {
            //key为注解的value，value为代理对象的引用实例
            Map<String,Object> map = new HashMap<>();
            //获取所有注解@Reference的类
            Set<Class> set = getReference();
            //实例化所有服务
            for(Class<?> clazz:set){
                Object object = clazz.newInstance();
                //key如com.garylee.rpc.service.ProductService
                //value为实例
                //ps.别给接口加注解!!!接口不能实例!!!
                map.put(clazz.getAnnotation(Service.class).value().getName(),object);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取service包内所有被@Reference注解的类
     * @return
     */
    public static Set<Class> getReference(){

        Set<Class> classSet = new HashSet<>();
        // TODO: 2019/3/10 0010 先使用绝对路径吧
//        String path = System.getProperty("user.dir")+"\\src\\main\\java\\"+servicePackage;
        URL resource = Thread.currentThread().getContextClassLoader().getResource(servicePackage);
        File file = new File(resource.getFile());
        System.out.println("service包路径:"+file.getAbsolutePath());


        File[] files = file.listFiles();
        //遍历service文件夹所有文件
        for(File f:files){
            if(f.getName().contains(".class")){
                //获取该文件绝对路径
                String paths = f.getAbsolutePath();
                //获取包名+类名(com.garylee.rpc.service.UserService)
                //"/"要转为"\\"，不然就抛出-1
                String className = paths.substring(paths.indexOf(servicePackage.replace("/","\\"))).replace(".class","").replace(File.separator,".");

                //如果该类被自定义注解@Reference所注解，则放入set集合
                try {
                    Class clazz = null;
                    //赋值并判断是否加了注解
                    if((clazz = Class.forName(className)).isAnnotationPresent(Service.class))
                        classSet.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return classSet;
    }
}
