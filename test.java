public class test {
    public static void main(String[]args){
        //获取委托类对象
        testDelegate testdelegate = new testDelegate();
        //获取代理类实例对象
        testDelegate proxy = (testDelegate) new testHandler(testdelegate).getProxyInstance();
        //执行代理对象的方法
        proxy.sayHello();
    }
}
