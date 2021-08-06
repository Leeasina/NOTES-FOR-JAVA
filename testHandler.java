package demotest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//继承InvocationHandler接口，实现调用处理器
public class testHandler implements InvocationHandler{
    private Object target;
    public testHandler(Object target){
        this.target = target;
    }

    //实现java.lang.reflect.InvocationHandler.invoke()方法
    public Object invoke(Object proxy,Method method,Object[] args) throws Throwable{
        //添加自定义的委托逻辑
        System.out.println("test DynamicProxy");
        //调用委托类的方法
        Object result = method. invoke(target,args);
        return result;
    }
}
