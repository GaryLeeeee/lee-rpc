# lee-rpc
简单实现一个rpc框架
### 介绍
RPC(Remote Procedure Call)，即远程过程调用。它是一种通过网络从远程计算机程序上请求服务,而不需要了解底层网络技术的协议。
简单来说，就是在一台电脑的应用层去请求另一台电脑的服务层。

### 工作原理
使用JDK动态代理(不了解可以[点击这里](https://www.jianshu.com/p/1682ed0d0c16))  
使用Socket通信(bio)  
使用反射来获取获取类对象  
使用注解来判断服务  

### 工作流程
![工作流程](https://github.com/GaryLeeeee/lee-rpc/blob/master/%E5%B7%A5%E4%BD%9C%E6%B5%81%E7%A8%8B.png)<br>
* 1.客户端Client调用ClientProxy方法，生成代理对象  
* 2.封装RpcRequest传给网络层RpcClient  
* 3.通过socket通信向RpcServer传输对象流  
* 4.服务器获得参数RpcRequest  
* 5.根据请求调用对应的服务里的方法  
* 6.返回结果到服务器  
* 7.封装成RpcResponse传给网络层RpcServer  
* 8.通过socket通信向RpcClient传输对象流  
* 9.客户端获得参数RpcResponse  
* 10.返回结果到客户端  

### 使用方法
在test包中找到Server运行，并运行Client。

```Java
public class Server {
    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.receive();
    }
}
```
```Java
public class Client {
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
```
### todo
* 包扫描时的路径尽量不使用太绝对的路径(√2019.3.15&nbsp;改为扫描target目录,对应的java文件要更换成class文件)
* bio通信->nio通信(毕竟nio效率比较高)
* 单线程->多线程->线程池(√2019.3.16&nbsp;加上线程池(ThreadPoolExecutor))
* 每次都是一开始就实例化好服务类，可能会占用太多内存，后面看看能不能需要用到才去实例化(单例模式)
* 直接输出service会抛出异常，待解决
* 目前@Reference注解的字段为static(保证可重复调用),后续可参考IOC实现单例
* 服务注册中心(如Zookeeper)
* 异步调用(如中间件)

