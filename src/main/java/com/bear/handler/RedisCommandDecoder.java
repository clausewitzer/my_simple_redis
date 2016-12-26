package com.bear.handler;

import com.bear.pojo.SimpleRedisCommand;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bear on 16-12-21.
 * 解析RESP协议 封装成 SimpleRedisCommand
 * <p>
 * RESP  简单协议备注
 * + 简单字符串  +Okay \r\n
 * - 错误信息 -wrong \r\n
 * : 整数 以 :1234 SADD 返回整数
 * $批量字符串 $1233 表示下一行字符串的长度   $-1 null 字符串
 * *数组 以*开头 表示消息体总共多少行
 * <p>
 * 例1 ============================================================
 * <p>
 * set bear lyf
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
 * 例2 ============================================================
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
 * lyf\r\n * 协议备注
 */
public class RedisCommandDecoder extends ReplayingDecoder<Void> {


    private static byte[] CRLF = new byte[]{'\r', '\n'};

    private List<byte[]> myCmds = null;
    private int needArgNum = 0; //需要读取多少个参数
    private int argNum = 0; //参数 查看 总共有多少行参数


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        if (myCmds == null) {
            if (byteBuf.readByte() == '*') {
                decodeLineNumbers(byteBuf); //解析协议头
            }
        } else {
            decodeArgs(byteBuf);//解析协议后段数据
        }

        if (isCommple()) {

            SimpleRedisCommand simpleRedisCommand = new SimpleRedisCommand(new String(myCmds.get(0)), myCmds);
            System.out.println("解析完成:" + simpleRedisCommand.toString());
            list.add(simpleRedisCommand);

            clear();

        }
    }

    private void decodeLineNumbers(ByteBuf byteBuf) {
        needArgNum = getIntNumber(byteBuf);//读取总共有多少行命令
        myCmds = new ArrayList<>();
        checkpoint();
    }

    //读取参数放到
    private void decodeArgs(ByteBuf byteBuf) {

        for (int i = argNum; i < needArgNum; i++) {
            if (byteBuf.readByte() == '$') {
                int paramSize = getIntNumber(byteBuf);
                byte[] cmdBytes = new byte[paramSize];
                byteBuf.readBytes(cmdBytes);  //尝试一次读取多个byte数组 从byte中
                byteBuf.skipBytes(2);
                myCmds.add(cmdBytes);
                checkpoint();//标记数据
            }
            argNum++;
        }
    }


    private boolean isCommple() {
        return (myCmds != null)
                && (argNum > 0)
                && (argNum == needArgNum);
    }


    /**
     * 读取协议中具体数据 例如 *3\r\n中的3
     *
     * @param byteBuf
     * @return
     */
    private int getIntNumber(ByteBuf byteBuf) {

        int integer = 0;
        char c;
        while ((c = (char) byteBuf.readByte()) != '\r') {
            integer = (integer * 10) + (c - '0');
        }
        byteBuf.skipBytes(1);//跳过 \n

        return integer;
    }


    private void clear() {
        needArgNum = 0;
        argNum = 0;
        myCmds = null;
    }

}
