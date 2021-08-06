import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

//代理类
public class testHandler implements MethodInterceptor {//继承MethodInterceptor接口
    private Object target;//维护一个目标对象
    public testHandler(Object target){
        this.target = target;               //传入委托类实例对象
    }
    //为目标对象生成代理对象
    public Object getProxyInstance(){       //该方法用于生成代理对象
        //实例化字节码增强器
        Enhancer en = new Enhancer();
        // 指定Enhancer 对象的父类（委托类）
        en.setSuperclass(target.getClass());
        // 指定Enhancer 对象的回调方法
        en.setCallback(this);
        // 生成代理对象
        return en.create();
    }
    public Object intercept(Object o , Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("test DynamicProxy");
        Object object = methodProxy.invokeSuper(o,objects);
        return object;
    }
}

