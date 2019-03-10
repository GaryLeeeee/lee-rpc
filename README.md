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
    public static void main(String[] args) {
        ClientProxy proxy = new ClientProxy();
        UserService userService = proxy.newProxyInstance(UserService.class);
        User user = new User();
        user.setName("teemo");
        user.setAge(12);
        System.out.println(userService.getUserInfo(user));
    }
}
```
### todo
* 包扫描时的路径尽量不使用太绝对的路径
* bio通信->nio通信(毕竟nio效率比较高)
* 单线程->多线程
* 每次都是一开始就实例化好服务类，可能会占用太多内存，后面看看能不能需要用到才去实例化(单例模式)



