package com.bear.storage;

import com.bear.handler.MainHandler;
import com.bear.pojo.SimpleRedisCommand;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bear on 16-12-24.
 */
public class RedisCoreInterfaceTest {


    @Test
    public void testReject() {

        RedisCoreInterface redisCoreInterFace = new RedisCoreImplByLocalMemory();

        Class<? extends RedisCoreInterface> clazz = redisCoreInterFace.getClass();

        for (Method method : clazz.getMethods()) {
            System.out.println(method.getName());
            Class<?>[] types = method.getParameterTypes();
            Object[] objects = new Object[types.length];

            System.out.println(types.length); //获取参数返回的类型
        }
    }

    @Test
    public void testMethodParams() {
        RedisCoreInterface rc = new RedisCoreImplByLocalMemory();
        Class<?> rcClass = rc.getClass();

        for (Method method : rcClass.getMethods()) {
            if (method.getName().equalsIgnoreCase("quit")) {
                System.out.println("--->"+method.getName());
                Class<?>[] type = method.getParameterTypes();
                System.out.println(type.toString()+"  "+type.length);
                Object[] objects = new Object[type.length];
                try {
                   method.invoke(rc,objects);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     *  测试反射执行的方法
     */
    @Test
    public  void  testExecutedMethod(){

        RedisCoreInterface redisCoreInterface = new RedisCoreImplByLocalMemory();
        MainHandler mainHandler = new MainHandler(redisCoreInterface);

        List<byte[]> myCmds = new ArrayList<>();
        myCmds.add("get".getBytes());
        myCmds.add("bearName".getBytes());
        SimpleRedisCommand simpleRedisCommand = new SimpleRedisCommand(new String(myCmds.get(0)), myCmds);

        try {
            mainHandler.getMethodMap().get("get").execute(simpleRedisCommand);

        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
