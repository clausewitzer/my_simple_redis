package com.bear;

import com.bear.server.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by bear on 16-12-21.
 */
public class Runner {



    public static void main(String[] args)  throws Exception{

        //TODO 启动redis netty主服务

        //TODO 启动 redis 日志监听线程 同步数据



         new Server().start(8888);
    }



}
