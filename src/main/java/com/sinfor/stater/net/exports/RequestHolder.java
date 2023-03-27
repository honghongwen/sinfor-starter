package com.sinfor.stater.net.exports;

import com.sinfor.stater.net.domain.Request;
import com.sinfor.stater.net.exception.RequestNotFoundException;

/**
 * @author fengwen
 * @date 2022/5/10
 * @description Request存储在ThreadLocal中
 **/
public class RequestHolder {

    private static final ThreadLocal<Request> REQ_LOCAL = new ThreadLocal<>();

    private RequestHolder() {

    }

    public static Request getReq() {
        Request req = REQ_LOCAL.get();
        if (req == null) {
            throw new RequestNotFoundException("未能获取到request");
        }
        return req;
    }

    public static void setReq(Request req) {
        REQ_LOCAL.set(req);
    }

    public static void remove() {
        REQ_LOCAL.remove();
    }
}
