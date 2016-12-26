package com.bear.pojo.replay;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by bear on 16-12-22.
 * 未找到合适的答复
 */
public class ErrorReply implements RedisReply {

    public static final ErrorReply NYI_REPLY = new ErrorReply("Not yet implemented");
    private final String error;
    public  ErrorReply(String error){
        this.error = error;
    }

    @Override
    public Object data() {
        return this.error;
    }

    @Override
    public void write(ByteBuf out) throws IOException {
            out.writeBytes(WRONG_MARK);
            out.writeBytes(error.getBytes(StandardCharsets.UTF_8));
            out.writeBytes(CRLF);
    }



}


