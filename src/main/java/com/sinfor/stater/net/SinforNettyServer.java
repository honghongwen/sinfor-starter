package com.sinfor.stater.net;

import com.sinfor.stater.net.init.ChannelInit;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component("SinforNettyServer")
public class SinforNettyServer {

    @Value("${sinfor.netty.port}")
    private Integer sinforNettyPort;

    private ServerBootstrap bootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;

    private static final AtomicInteger BOSS_THREAD_COUNT = new AtomicInteger(0);
    private static final AtomicInteger WORK_THREAD_COUNT = new AtomicInteger(0);

    public ChannelFuture startServer() {
        log.info(Thread.currentThread() + "启动sinfor netty服务中...." + sinforNettyPort);
        bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(2, r -> {
            return new Thread(r, "thread-sinfor-boss-" + BOSS_THREAD_COUNT.incrementAndGet());
        });
        workGroup = new NioEventLoopGroup(1, r -> {
            return new Thread(r, "thread-sinfor-work-" + WORK_THREAD_COUNT.incrementAndGet());
        });

        ChannelFuture future = null;

        try {
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInit());
            future = bootstrap.bind(8021).sync();
            log.info("sinfor netty服务启动成功...端口号为:" + 8021);
        } catch (InterruptedException e) {
            log.error("sinfor netty服务启动失败...", e);
        }
        return future;
    }

    public void close() {
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
        log.info("sinfor netty关闭完成...");
    }

}
