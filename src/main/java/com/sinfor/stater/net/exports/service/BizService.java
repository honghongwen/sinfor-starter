package com.sinfor.stater.net.exports.service;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author fengwen
 * @date 17:16 2022/4/27
 * @description 提供给引用包实现的spi，BizHandler会调用spi中方法,spi需服务提供方具体实现
 **/
public interface BizService {

    /**
     * @author fengwen
     * @date 14:20 2022/5/10
     * @description 初始化BizHandler的channel时调用，可用于如清除session
     * @param
     * @return void
     **/
    void initChannel(ChannelHandlerContext ctx);

    /**
     * @author fengwen
     * @date 14:20 2022/5/10
     * @description 销毁BizHandler的channel时调用，可用于如清除session
     * @param
     * @return void
     **/
    void destroyChannel(ChannelHandlerContext ctx);
}
