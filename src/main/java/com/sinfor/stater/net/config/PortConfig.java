package com.sinfor.stater.net.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sinfor.nety")
public class PortConfig {

    /**
     * tcp启动端口
     */
    private Integer port;

}
