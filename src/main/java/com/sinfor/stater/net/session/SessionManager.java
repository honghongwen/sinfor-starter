package com.sinfor.stater.net.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @author fengwen
 * @date 2022/5/17
 * @description TODO
 **/
public class SessionManager {

    public static final AttributeKey<XfSocketSession> ATTACHMENT_KEY = AttributeKey.valueOf("ATTACHMENT_KEY");


    public static boolean putConnSession(Channel channel) {
        ConnInfo connInfo = new ConnInfo();
        XfSocketSession session = new XfSocketSession();
        session.setConnInfo(connInfo);
        session.setChannel(channel);
        session.setSessionId(channel.id().toString());
        InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.remoteAddress();
        connInfo.setHost(inetSocketAddress.getAddress().getHostAddress());
        connInfo.setPort(inetSocketAddress.getPort());

        setAttachment(channel, session);
        return false;
    }


    /**
     * 移除
     */
    public static Session removeSession(Channel channel) {
        removeAttachment(channel);
        return null;
    }


    /**
     * 设置Attachment
     *
     * @param channel
     * @param session
     */
    public static void setAttachment(Channel channel, XfSocketSession session) {
        channel.attr(ATTACHMENT_KEY).set(session);
    }

    /**
     * 获取Attachment
     *
     * @param channel
     * @return
     */
    public static XfSocketSession getAttachment(Channel channel) {
        return channel.attr(ATTACHMENT_KEY).get();
    }

    /**
     * 清除Attachment
     *
     * @param channel
     */
    public static void removeAttachment(Channel channel) {
        channel.attr(ATTACHMENT_KEY).set(null);
    }

}
