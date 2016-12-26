package com.bear.pojo.replay;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by bear on 16-12-22.
 */
public interface RedisReply<T> {
    byte[] CRLF = new byte[] { '\r', '\n' };


    //常规答复协议
    final  byte[] INT_MARK ={':'};
    final  byte[] $_MARK ={'$'};
    final  byte[] WRONG_MARK = {'-'};


    /***
     * RESP  简单协议备注
     * + 简单字符串  +Okay \r\n
     * <p>
     * - 错误信息 -wrong \r\n
     * <p>
     * : 整数 以 :1234 SADD 返回整数
     * <p>
     * $批量字符串 $1233 表示下一行字符串的长度   $-1 null 字符串
     * <p>
     * *数组 以*开头 表示消息体总共多少行
     * <p>
     * <p>
     * <p>
     * ============================================================
     * set bear lyf
     * <p>
     * //请求
     * *3\r\n
     * $3\r\n
     * set\r\n
     * $4\r\n
     * bear\r\n
     * $3\r\n
     * lyf\r\n
     * <p>
     * //返回
     * :1\r\n
     * <p>
     * ============================================================
     * <p>
     * //请求
     * get bear
     * *2\r\n
     * $3\r\n
     * get\r\n
     * $4\r\n
     * bear\r\n
     * <p>
     * //返回
     * $3\r\n
     * lyf\r\n
     * <p>
     * //无信息返回
     *
     *
     *
     ************************************/



    T data();

    void write(ByteBuf out) throws IOException;
}
