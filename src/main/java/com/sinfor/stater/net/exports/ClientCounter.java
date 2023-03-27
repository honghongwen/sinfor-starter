package com.sinfor.stater.net.exports;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fengwen
 * @date 2022/5/4
 * @description 客户端计数
 **/
public class ClientCounter {

    public static AtomicInteger CLIENT = new AtomicInteger(0);

    public static Map<String, String> CLIENT_MAP = new ConcurrentHashMap<>();

}
