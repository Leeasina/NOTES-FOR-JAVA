package demotest;

//目标类，继承接口，实现接口的方法
//如果想要不改变该类的代码的情况下实现其他功能 需通过Java代理模式来实现
//在Java代理中，该类被称为委托类
public class testDelegate implements testImpl{
    public void sayHello(){
        System.out.println("Hello LeEas1na !!");
    }
}
