package com.bear.handler;

import com.bear.pojo.replay.BulkReply;
import com.bear.pojo.replay.ErrorReply;
import com.bear.pojo.replay.IntegerReply;
import com.bear.pojo.SimpleRedisCommand;
import com.bear.pojo.replay.RedisReply;
import com.bear.storage.MethodWrapper;
import com.bear.storage.RedisCoreImplByLocalMemory;
import com.bear.storage.RedisCoreInterface;
import com.bear.storage.SimpleRedisException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bear on 16-12-21.
 */
public class MainHandler extends SimpleChannelInboundHandler<SimpleRedisCommand> {


    //构造执行方法的Map
    private Map<String, MethodWrapper> methodMap = new HashMap<>(15);

    public Map<String, MethodWrapper> getMethodMap() {
        return methodMap;
    }

    //构造器
    public MainHandler(final RedisCoreInterface redisCoreInterface) {


        //TODO 此处改成以反射方式获取对象数据


        Class<? extends RedisCoreInterface> clazz = redisCoreInterface.getClass();
        for (Method method : clazz.getMethods()) {
            Class<?>[] type = method.getParameterTypes();

            /***
             * 组成以 K：方法名 V:MethodWrapper对象 的hashMap
             * 在read 拿到name后 时候直接 get(name).execute(command) 返回对应的处理处理结果
             */
            methodMap.put(method.getName(), new MethodWrapper() {

                public RedisReply execute(SimpleRedisCommand command) {
                    Object[] objects = new Object[type.length];
                    try {
                        command.setInvokeParams(type, objects);
                        return (RedisReply) method.invoke(redisCoreInterface, objects);
                    } catch (Exception e) {
                        //TODO 此次异常需要重新构造
                        e.printStackTrace();
                        return new ErrorReply("ERR " + e.getMessage());
                    }
                }
            });
        }

    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SimpleRedisCommand msg) throws Exception {

        System.out.println("RedisCommandHandler: " + msg);
        String commandName = msg.getName();
        if ("quit".equalsIgnoreCase(commandName) || "exit".equalsIgnoreCase(commandName)) {
            ctx.close(); //关闭
        } else {
            RedisReply returnReply = null;
            MethodWrapper wrapper = methodMap.get(commandName);
            if (wrapper == null) {
                returnReply = new ErrorReply("unsupport command " + commandName);
            }
            try {
                returnReply = wrapper.execute(msg);
                if (returnReply == null) {
                    returnReply = new ErrorReply("not implemet command " + commandName);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                returnReply = new ErrorReply("not implemetsss command " + commandName);
            }

            ctx.writeAndFlush(returnReply);
        }
    }

    //捕获异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


}
