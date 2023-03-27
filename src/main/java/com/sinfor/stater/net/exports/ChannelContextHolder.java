package com.sinfor.stater.net.exports;

import com.sinfor.stater.net.exception.ChannelContextNotFoundException;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author fengwen
 * @date 2022/5/10
 * @description ctx不能用ThreadLocal进行存储，因为同一个work线程可以处理多个连接，会导致在连接中无法取到对应的ctx.
 **/
public class ChannelContextHolder {

    private static final ThreadLocal<ChannelHandlerContext> CTX_LOCAL = new ThreadLocal<>();

    private ChannelContextHolder() {

    }

    public static ChannelHandlerContext getChannelCtx() {
        ChannelHandlerContext ctx = CTX_LOCAL.get();
        if (ctx == null) {
            throw new ChannelContextNotFoundException("未能获取到channel context");
        }
        return ctx;
    }

    public static void setChannelCtx(ChannelHandlerContext ctx) {
        CTX_LOCAL.set(ctx);
    }

    public static void remove() {
        CTX_LOCAL.remove();
    }
}
