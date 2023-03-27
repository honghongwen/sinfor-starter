package com.sinfor.stater.net.session;

import lombok.Data;

import java.util.Date;

/**
 * @author fengwen
 * @date 2022/5/17
 * @description TODO
 **/
@Data
public class ConnInfo {

    private String host;
    private int port;
    private Date connTime;
    private Date keepAliveTime;
    private int packageIndex;
    private int tryPwdCount;


    public ConnInfo() {
        Date now = new Date();
        connTime = now;
        keepAliveTime = now;
        packageIndex = 0;
    }

}
