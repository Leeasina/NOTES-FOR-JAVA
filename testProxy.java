package demotest;

//代理类
public class testProxy implements testImpl{
    //继承接口
    private testImpl target;
    //传入委托类的实例对象
    public testProxy(testDelegate target){
        this.target = target;
    }
    public void sayHello(){
        System.out.println("Let's say hello!");
        //实现接口时调用委托类的方法
        target.sayHello();
    }
}
