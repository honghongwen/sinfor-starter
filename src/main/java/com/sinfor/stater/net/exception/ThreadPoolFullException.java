package com.sinfor.stater.net.exception;

/**
 * @author fengwen
 * @date 2022/5/10
 * @description 业务线程池满了异常
 **/
public class ThreadPoolFullException extends RuntimeException {

    public ThreadPoolFullException(String message) {
        super(message);
    }
}
