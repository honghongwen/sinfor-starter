package com.sinfor.stater.net.handler;

import com.sinfor.stater.net.domain.Request;
import com.sinfor.stater.net.domain.Response;
import com.sinfor.stater.net.exception.BusinessException;
import com.sinfor.stater.net.exception.ThreadPoolFullException;
import com.sinfor.stater.net.exports.ChannelContextHolder;
import com.sinfor.stater.net.exports.RequestHolder;
import com.sinfor.stater.net.exports.service.BizService;
import com.sinfor.stater.net.exports.service.HandlerService;
import com.sinfor.stater.net.processor.DataProcessor;
import com.sinfor.stater.net.session.Session;
import com.sinfor.stater.net.session.SessionManager;
import com.sinfor.stater.net.session.XfSocketSession;
import com.sinfor.stater.net.support.SpringUtil;
import com.sinfor.stater.net.threadfactory.BizThreadFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fengwen
 * @date 12:55 2022/4/27
 * @description 业务处理
 **/
@Slf4j
public class BizHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("channelId为：{}", ctx.channel().id().toString());

        Request request = (Request) msg;
        // 业务逻辑和work thread区别开。
        ThreadPoolExecutor bizPool = BizThreadFactory.getInstance().bizPool;
        try {
            bizPool.submit(() -> {
                try {
                    task(request, ctx);
                } catch (BusinessException e) {
                    log.error("业务执行异常", e);
                    Response response = Response.getInstance(request).fail(e.getMessage()).encrypt();
                    ctx.writeAndFlush(response);
                } catch (Exception e) {
                    log.error("服务器内部错误", e);
                    Response response = Response.getInstance(request).fail("服务器内部错误").encrypt();
                    ctx.writeAndFlush(response);
                } finally {
                    // remove request thread local value.
                    log.debug("-------移除ctx holder---------");
                    ChannelContextHolder.remove();
                    log.debug("-------移除request holder---------");
                    RequestHolder.remove();
                    Session.SESSION_CONTEXT.remove();
                }
            });
        } catch (ThreadPoolFullException e) {
            log.error("业务线程池已满", e);
            Response response = Response.getInstance(request).fail(e.getMessage()).encrypt();
            ctx.writeAndFlush(response);
        }

    }

    private void task(Request request, ChannelHandlerContext ctx) throws Exception {
        // 1. decrypt request data
        if (request.getTempData() != null) {
            // syntax request data from temp data's byte array.
            byte[] data = DataProcessor.decrypt(request.getTempData(), true, request.isCompress(), false);
            request.setData(new String(data, StandardCharsets.UTF_8));
            request.setTempData(null);
        } else {
            log.error("tempdata is null");
        }

        XfSocketSession session = SessionManager.getAttachment(ctx.channel());
        if (session == null) {
            ctx.close();
            return;
        }
        session.setRequest(request);

        // 2. enter request thread local value.
        log.debug("-------设置request holder---------");
        RequestHolder.setReq(request);
        log.debug("-------设置ctx holder---------");
        ChannelContextHolder.setChannelCtx(ctx);
        Session.SESSION_CONTEXT.set(session);

        // 3. execute multi method of handler service.
        HandlerService service = SpringUtil.getBean(HandlerService.class);
        service.before();
        Object result = service.execute();
        service.after(result);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        BizService service = SpringUtil.getBean(BizService.class);
        service.initChannel(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        BizService service = SpringUtil.getBean(BizService.class);
        service.destroyChannel(ctx);
        super.channelInactive(ctx);
    }
}
