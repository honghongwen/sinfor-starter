package com.sinfor.stater.net.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@ConfigurationProperties(prefix = "sinfor.netty.idle")
public class IdleConfig {

    /**
     * 没有读操作，则断开连接
     */
    private Integer readTimeout;

    /**
     * 没有写操作，则断开连接
     */
    private Integer writeTimeout;

    /**
     * 读&&写操作，断开连接
     */
    private Integer allTimeout;
}
