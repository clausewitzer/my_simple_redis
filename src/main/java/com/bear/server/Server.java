package com.bear.server;

import com.bear.handler.MainHandler;
import com.bear.handler.RedisCommandDecoder;
import com.bear.handler.RedisReplyEncoder;
import com.bear.storage.RedisCoreImplByLocalMemory;
import com.bear.storage.RedisCoreInterface;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by bear on 16-12-21.
 */
public class Server {


    //初始化存储引擎
    private RedisCoreInterface initStore() throws Exception {

        Properties prop = new Properties();
        InputStream in = Object.class.getResourceAsStream("/simple_redis.properties");
        prop.load(in);
        String storeClassName = prop.getProperty("simpleRedisStoreClass").trim();

        Class<?> clazz = Class.forName(storeClassName);
        return  (RedisCoreInterface) clazz.newInstance();
    }


    //服务器启动
    public void start(int port) throws Exception {

        final RedisCoreInterface redisCoreInterface =initStore();

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group).channel(NioServerSocketChannel.class).localAddress(port)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new RedisCommandDecoder())
                                    .addLast(new RedisReplyEncoder())
                                    .addLast(new MainHandler(redisCoreInterface));
                        }
                    });

            //binds server waits for server to close and release source
            ChannelFuture f = b.bind().sync();
            System.out.println(Server.class.getName() + " Started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync(); //关闭服务
        }
    }

}
