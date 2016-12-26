package com.bear;

import com.bear.server.Server;
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
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by bear on 16-12-21.
 */
public class Boot {


    public static void main(String[] args) throws Exception {


        new Server().start(8888);
    }




}
