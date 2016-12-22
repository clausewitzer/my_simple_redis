package com.bear.pojo;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by bear on 16-12-22.
 */
public class BulkReply implements RedisReply<byte[]> {

    public static final BulkReply NIL_REPLY = new BulkReply();

    private final byte[] data;

    private final int len;

    public BulkReply() {
        this.data = null;
        this.len = -1;
    }

    public BulkReply(byte[] data){
        this.data =data;
        this.len = data.length;
    }


    @Override
    public byte[] data() {
        return this.data;
    }

    @Override
    public void write(ByteBuf out) throws IOException {
            out.writeBytes($_MARK);
            out.writeBytes(String.valueOf(len).getBytes());
            out.writeBytes(CRLF);//

            if(len>0)
            {
                out.writeBytes(data);
                out.writeBytes(CRLF);
            }
    }


    @Override
    public String toString() {
        return "BulkReply{" +
                "bytes=" + Arrays.toString(data) +
                '}';
    }
}
