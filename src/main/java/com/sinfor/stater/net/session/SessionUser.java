package com.sinfor.stater.net.session;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fengwen
 * @date 2022/5/17
 * @description TODO
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SessionUser extends XfUser {
    /**
     * 当前登陆平台
     */
    private int platform;
    /**
     * 全局数据;如上次公告提醒时间
     */
    private Object data;
    /**
     * 客户端版本
     */
    private double clientVersion;

}
