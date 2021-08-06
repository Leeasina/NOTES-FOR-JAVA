➜  脚本 java -jar SerializationDumper-v1.13.jar aced0005737200216f72672e6170616368652e736869726f2e746573742e546573745461726765743100000000000000010200014c00037374727400124c6a6176612f6c616e672f537472696e673b787074000668656c6c6f21
```
STREAM_MAGIC - 0xac ed              //Java 序列化字符串魔术幻数   
STREAM_VERSION - 0x00 05            //Java 序列化版本号
Contents
  TC_OBJECT - 0x73                  //新的对象
    TC_CLASSDESC - 0x72             //新的类
      className
        Length - 33 - 0x00 21       //长度 33个字符
        Value - org.apache.shiro.test.TestTarget1 - 0x6f72672e6170616368652e736869726f2e746573742e5465737454617267657431   //类名
      serialVersionUID - 0x00 00 00 00 00 00 00 01          //serialVersionUID 属性值
      newHandle 0x00 7e 00 00
      classDescFlags - 0x02 - SC_SERIALIZABLE           //Java 序列化属性标识符，如果被序列化的类继                               承了 Serializable 接口 , 则设置该标志0x02 , 并在反序列化时调用的类也需要继承 Serializable 接口 , 并使用默认的反序列化机制
      fieldCount - 1 - 0x00 01              //类中字段的个数
      Fields
        0:
          Object - L - 0x4c                 //字段类型的类签名 
          fieldName
            Length - 3 - 0x00 03            //类中属性字段的长度:str == 3
            Value - str - 0x737472          //类中属性字段的值 s t r == 73 74 72
          className1
            TC_STRING - 0x74                //String类型
              newHandle 0x00 7e 00 01
              Length - 18 - 0x00 12         //String类型标准签名长度即“Ljava/lang/String;”的长度
              Value - Ljava/lang/String; - 0x4c6a6176612f6c616e672f537472696e673b
      classAnnotations
        TC_ENDBLOCKDATA - 0x78              //一个数据块的结束符号
      superClassDesc
        TC_NULL - 0x70                      //用于在流中指定 Null 引用
    newHandle 0x00 7e 00 02
    classdata
      org.apache.shiro.test.TestTarget1
        values
          str
            (object)
              TC_STRING - 0x74
                newHandle 0x00 7e 00 03
                Length - 6 - 0x00 06        //属性字段值的长度
                Value - hello! - 0x68656c6c6f21     //属性值
                
```
