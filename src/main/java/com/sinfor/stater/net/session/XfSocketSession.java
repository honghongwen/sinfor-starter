package com.sinfor.stater.net.session;

import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author fengwen
 * @date 2022/5/17
 * @description TODO
 **/
@Data
public class XfSocketSession extends Session {

    private Channel channel;
    private ConnInfo connInfo;
    private String sessionId;


}
