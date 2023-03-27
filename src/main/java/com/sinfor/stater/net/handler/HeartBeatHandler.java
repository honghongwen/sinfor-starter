package com.sinfor.stater.net.handler;

import com.sinfor.stater.net.domain.Request;
import com.sinfor.stater.net.domain.Response;
import com.sinfor.stater.net.session.SessionManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Objects;

import static com.sinfor.stater.net.constants.SysModuleCmd.HEART_BEAT_CMD;
import static com.sinfor.stater.net.constants.SysModuleCmd.SYSTEM_MODULE;
import static com.sinfor.stater.net.exports.ClientCounter.CLIENT;
import static com.sinfor.stater.net.exports.ClientCounter.CLIENT_MAP;

/**
 * @author fengwen
 * @date 2022/4/25
 * @description TODO
 **/
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读写时间触发器
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.ALL_IDLE == event.state()) {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                log.info("客户端[{}]:[{}]已经很久没进行读写了，主动关闭客户端...",
                        address.getAddress().getHostAddress(),
                        ctx.channel().id());
                ctx.close();
            }

        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 系统模块处理器，包含心跳、获取服务器时间、以及获取sessionId
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Request request = (Request) msg;
        Channel channel = ctx.channel();
        InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();

        if (Objects.equals(request.getModule(), SYSTEM_MODULE)) {
            if (Objects.equals(request.getCmd(), HEART_BEAT_CMD)) {
                log.debug("客户端[{}]心跳...", address.getAddress().getHostAddress());
                Response response = Response.getInstance(request).ok("收到心跳数据").encrypt();
                ctx.writeAndFlush(response);
            }
        }

        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端计数,单节点
        CLIENT.getAndIncrement();
        Channel channel = ctx.channel();
        InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
        CLIENT_MAP.put(channel.id() + "", address.getAddress().getHostAddress());
        SessionManager.putConnSession(channel);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        CLIENT.getAndDecrement();
        Channel channel = ctx.channel();
        InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
        CLIENT_MAP.remove(channel.id() + "");
        SessionManager.removeSession(ctx.channel());
        super.channelInactive(ctx);
    }
}
