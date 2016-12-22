package com.bear.pojo;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by bear on 16-12-22.
 */
public interface RedisReply<T> {
    byte[] CRLF = new byte[] { '\r', '\n' };

    final  byte[] INT_MARK ={':'};
    final  byte[] $_MARK ={'$'};

    T data();

    void write(ByteBuf out) throws IOException;
}
