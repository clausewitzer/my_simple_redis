package com.bear.pojo.replay;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by bear on 16-12-23.
 * 返回多个结果的key
 */
public class MultiBulkReply implements RedisReply {

    public static final byte[] MARKER = {'*'};

    private Set<String> replies;


    byte[] NEG_ONE_WITH_CRLF = {'-', '1', '\r', '\n'};


    //简单实现 暂时只支持 set集合处理问题
    public MultiBulkReply(Set<String> replies) {
        this.replies = replies;
    }


    @Override
    public Object data() {
        return this.replies;
    }


    /**
     * 以redis协议写入多个数据
     *
     * @param out
     * @throws IOException
     */
    @Override
    public void write(ByteBuf out) throws IOException {

        out.writeBytes(MARKER);
        if (replies == null || replies.size() == 0) {
            out.writeBytes(NEG_ONE_WITH_CRLF);//返回空的数据
        } else {
            Integer len = replies.size();
            out.writeBytes(len.toString().getBytes());
            out.writeBytes(CRLF);
            //依次写入对应的数据
            for (String str : replies) {
                out.writeBytes($_MARK);
                out.writeBytes(String.valueOf(str.length()).getBytes());
                out.writeBytes(CRLF);//
                out.writeBytes(str.getBytes());
                out.writeBytes(CRLF);
            }

        }

    }


}
