package demotest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

//测试类，生产委托类实例对象，并将该对象传入代理类构造函数中
public class test {
    public static void main(String[] args) {
//        testDelegate ti = new testDelegate();
//        testProxy tp = new testProxy(ti);
//        tp.sayHello();
        //获取委托类对象
        testDelegate testdelegate = new testDelegate();

        //获取ClassLoader
        ClassLoader classloader = testdelegate.getClass().getClassLoader();

        //获取所有接口
        Class[] interfaces = testdelegate.getClass().getInterfaces();

        //获取一个调用处理器
        InvocationHandler invocationhandler =  new testHandler(testdelegate);

        //查看生成的代理类
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //创建代理对象
        testImpl proxy = (testImpl) Proxy.newProxyInstance(classloader, interfaces, invocationhandler);
        proxy.sayHello();
    }
}
