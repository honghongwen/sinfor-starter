package com.sinfor.stater.net.handler;

import com.sinfor.stater.net.domain.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static com.sinfor.stater.net.constants.Const.HEADER_FLAG;

/**
 * 数据包解码器
 *
 * <pre>
 * 数据包格式
 * 包头	|  数据包序号      |  模块版本号      |  模块号      |  命令号    |   签名     |   是否压缩     |   长度     |   数据
 * </pre>
 * <p>
 * 包头4字节 数据包序号4字节 模块版本号3个字节 模块号2字节 命令号2字节 签名24字节 长度4字节(数据部分占有字节数量)
 * 数据包序号用来避免数据包重发 version="1.1" sign=XpRI3Lt5E5rgqai6GzhIhg==
 * 通过String.getBytes()获取长度
 *
 * @author 李新周
 */
@Slf4j
public class RequestDecoder extends ByteToMessageDecoder {
    /**
     * 数据包基本长度
     */
    public static int BASE_LENGTH = 4 + 4 + 3 + 4 + 2 + 24 + 1 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
        String tryToStr = buffer.toString(StandardCharsets.UTF_8);
        if (buffer.readableBytes() < BASE_LENGTH) {
            log.error("错误数据-[数据长度错误]:{}", tryToStr);
            return;
        }
        try {
            buffer.markReaderIndex();
            int header = buffer.readInt();
            if (header != HEADER_FLAG) {
                log.error("错误数据-[数据头错误]:{}", tryToStr);
                ctx.channel().close();
                return;
            }
            // 包序号
            int sequence = buffer.readInt();
            // 包版本
            byte[] bytes = new byte[3];
            buffer.readBytes(bytes);
            String version = new String(bytes, StandardCharsets.UTF_8);
            // 模块
            int module = buffer.readInt();
            // 命令
            short cmd = buffer.readShort();
            // 签名
            bytes = new byte[24];
            buffer.readBytes(bytes);
            byte[] sign = bytes;
            // 是否压缩
            boolean compress = buffer.readBoolean();
            // 包长度
            int bodyLength = buffer.readInt();
            if (bodyLength < 0) {
                ctx.channel().close();
                return;
            }
            if (buffer.readableBytes() < bodyLength) {
                buffer.resetReaderIndex();
                return;
            }
            // 包内容
            byte[] bodyBytes = new byte[bodyLength];
            buffer.readBytes(bodyBytes);

            Request message = new Request();
            message.setVersion(version);
            message.setSequence(sequence);
            message.setModule(module);
            message.setCmd(cmd);
            message.setSign(new String(sign, StandardCharsets.UTF_8));
            message.setCompress(compress);
            message.setTempData(bodyBytes);
            log.debug("[request decode]:\n" + message);

            out.add(message);
        } catch (Exception e) {
            log.error("解码数据异常..." + tryToStr);
            log.error("解码数据异常...", e);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String prefix = ctx.channel().id() + "-";
        // 使用channelId做前缀 去掉uuid中的-
        String traceId = prefix + UUID.randomUUID().toString().replaceAll("-", "");
        MDC.put("traceId", traceId);
        super.channelRead(ctx, msg);
    }
}
