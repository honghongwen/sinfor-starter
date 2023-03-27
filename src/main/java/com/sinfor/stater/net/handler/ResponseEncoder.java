package com.sinfor.stater.net.handler;

import cn.hutool.json.JSONUtil;
import com.sinfor.stater.net.domain.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import static com.sinfor.stater.net.constants.Const.HEADER_FLAG;

/**
 * <pre>
 * 数据包格式
 * |  包头	|  数据包序号      |  模块号      |  命令号    |  结果码    |  签名      |  是否压缩      |  长度       |   数据     |
 * </pre>
 *
 * @author 李新周
 */
@Slf4j
public class ResponseEncoder extends MessageToByteEncoder<Response> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Response response, ByteBuf buffer) {
        log.debug("[response encode]:\n{}", response);
        buffer.writeInt(HEADER_FLAG);
        buffer.writeShort(response.getSequence());
        buffer.writeInt(response.getModule());
        buffer.writeShort(response.getCmd());
        buffer.writeInt(response.getStateCode());
        buffer.writeBytes(response.getSign());
        buffer.writeBoolean(response.isCompress());

        byte[] bytes = (byte[]) response.getData();
        int length = bytes == null ? 0 : bytes.length;

        if (length <= 0) {
            buffer.writeInt(length);
        } else {
            buffer.writeInt(length);
            buffer.writeBytes(bytes);
        }

        if (response.getJMeter() != null) {
            buffer.writeBytes(response.getJMeter());
        }
    }

}
