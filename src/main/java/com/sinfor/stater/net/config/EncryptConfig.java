package com.sinfor.stater.net.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author fengwen
 * @date 2022/5/9
 * @description TODO
 **/
@Component
@ConfigurationProperties(prefix = "sinfor.encrypt")
public class EncryptConfig {

    private static long enabledPackageSize;
    private static boolean useEncryptResponse;
    private static String encryptKey;

}
