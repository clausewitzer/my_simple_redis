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
 * 解析redis协议
 */
public class RedisCommandDecoder extends ReplayingDecoder<Void> {


    //定义换行符
    private static final byte[] _t = "\r\n".getBytes();
    private static final byte _R = _t[0];
    private static final byte _N = _t[1];


    /**
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
     */
    private List<byte[]> myCmds = null;//
    private int needArgNum = 0; //需要读取多少个参数
    private int argNum = 0; //参数 查看 总共有多少行参数


    //此方法会被不停的调用读取
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {


        if (myCmds == null) {
            if (byteBuf.readByte() == '*') {
                decodeLineNumbers(byteBuf);
            }
        } else {
            //解析协议中参数
            decodeArgs(byteBuf);
        }

        //校验是否已经读取完毕
        if (isCommple()) {
            //构造自定义协议
            SimpleRedisCommand simpleRedisCommand = new SimpleRedisCommand(new String(myCmds.get(0)),myCmds);
            System.out.println("解析完成:"+simpleRedisCommand.toString());
            list.add(simpleRedisCommand);


            //清除历史标记
            needArgNum = 0;
            argNum =0;
            myCmds =null;

        }


    }


    /************************************************
     * 华丽丽的分割线
     *****************************************************/

    private void decodeLineNumbers(ByteBuf byteBuf) {

//        //尝试一个字节一个字节去读
//        byte tempByte ;
//        while ( (tempByte =byteBuf.readByte())!= _R )
//        {
//            tempList.add(tempByte);
//        }

        needArgNum = getIntNumber(byteBuf);//读取总共有多少行命令
        myCmds = new ArrayList<>();
        System.out.println("---->"+needArgNum);
        checkpoint();
    }

    //读取参数放到
    private void decodeArgs(ByteBuf byteBuf) {

        for (int i = argNum; i < needArgNum; i++) {
            if (byteBuf.readByte() == '$') {
                int paramSize = getIntNumber(byteBuf);
                byte[] cmdBytes = new byte[paramSize];
                //尝试一次读取多个byte数组 从byte中
                byteBuf.readBytes(cmdBytes);
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


    //读取  .3/r/n 中的3
    private int getIntNumber(ByteBuf byteBuf) {

        int integer = 0;
        char c;
        while ((c = (char) byteBuf.readByte()) != '\r') {
            integer = (integer * 10) + (c - '0');
        }
        byteBuf.skipBytes(1);//跳过 \n

        return integer;
    }

}
