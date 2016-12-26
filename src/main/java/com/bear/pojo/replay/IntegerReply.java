package com.bear.pojo.replay;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by bear on 16-12-22.
 */
public class IntegerReply implements RedisReply {

    private final int data;

    public IntegerReply(int data) {
        this.data = data;
    }


    @Override
    public Object data() {
        return this.data;
    }

    @Override
    public void write(ByteBuf out) throws IOException {
        out.writeBytes(INT_MARK);
        out.writeBytes(String.valueOf(data).getBytes());
        out.writeBytes(CRLF);
    }
}
