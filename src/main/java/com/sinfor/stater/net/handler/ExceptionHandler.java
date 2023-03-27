package com.sinfor.stater.net.handler;

import com.sinfor.stater.net.domain.Response;
import com.sinfor.stater.net.exception.BusinessException;
import com.sinfor.stater.net.exports.ChannelContextHolder;
import com.sinfor.stater.net.exports.RequestHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author fengwen
 * @date 2022/4/25
 * @description TODO
 **/
@Slf4j
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            IOException e = (IOException) cause;
            if ("远程主机强迫关闭了一个现有的连接。".equals(e.getMessage())) {
                log.info("客户端[{}]强制退出", ctx.channel().id());
            }
        } else {
            log.error("sinfor处理器错误:", cause);
        }
        ctx.close();
    }
}
