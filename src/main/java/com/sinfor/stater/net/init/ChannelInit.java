package com.sinfor.stater.net.init;

import com.sinfor.stater.net.handler.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * @author  fengwen
 * @date 2022/4/25
 * @description ChannelInitializer
 **/
@Slf4j
public class ChannelInit extends ChannelInitializer<SocketChannel> {


    /**
     * @author fengwen
     * @date 11:50 2022/4/25
     * @description 设置pipeline上的handler链
     * @param
     * @return void
     **/
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pip = ch.pipeline()
                // 换行符解决半包问题
//                .addLast(new LineBasedFrameDecoder(1024))
                // 自定义协议解决半包问题
                .addLast(new RequestDecoder())
                .addLast(new ResponseEncoder())
                // 转字符串
//                .addLast(new StringDecoder())
//                .addLast(new StringEncoder())
                // 心跳
                .addLast(new IdleStateHandler(10, 10, 10))
                .addLast(new HeartBeatHandler())
                // 业务处理
                .addLast(new BizHandler())
                .addLast(new ExceptionHandler());

        pip.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                MDC.remove("traceId");
                Channel channel = pip.channel();
                log.info("关闭连接监听器：[{}]关闭了连接", channel.id());
            }
        });
    }
}
