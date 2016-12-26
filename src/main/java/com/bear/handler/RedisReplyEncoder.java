package com.bear.handler;

import com.bear.pojo.replay.RedisReply;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by bear on 16-12-22.
 */
public class RedisReplyEncoder extends MessageToByteEncoder<RedisReply>  {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RedisReply msg, ByteBuf byteBuf) throws Exception {
        //编码消息准备写入
        System.out.println("编码后的消息: " + msg);
        System.out.println();
        msg.write(byteBuf);

    }
}
