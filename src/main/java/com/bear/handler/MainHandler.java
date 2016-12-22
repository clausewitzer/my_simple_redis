package com.bear.handler;

import com.bear.pojo.BulkReply;
import com.bear.pojo.IntegerReply;
import com.bear.pojo.SimpleRedisCommand;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.HashMap;

/**
 * Created by bear on 16-12-21.
 */
public class MainHandler extends SimpleChannelInboundHandler<SimpleRedisCommand> {

    private HashMap<String, byte[]> database = new HashMap<String, byte[]>();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SimpleRedisCommand msg) throws Exception {
        System.out.println("RedisCommandHandler: " + msg);

        if (msg.getName().equalsIgnoreCase("set")) {
            if (database.put(new String(msg.getArg1()), msg.getArg2()) == null) {
                ctx.writeAndFlush(new IntegerReply(1));
            } else {
                ctx.writeAndFlush(new IntegerReply(0));
            }
        }
        else if (msg.getName().equalsIgnoreCase("get")) {
            byte[] value = database.get(new String(msg.getArg1()));
            if (value != null && value.length > 0) {
                ctx.writeAndFlush(new BulkReply(value));
            } else {
                ctx.writeAndFlush(BulkReply.NIL_REPLY);
            }
        }


    }

    //捕获异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


}
