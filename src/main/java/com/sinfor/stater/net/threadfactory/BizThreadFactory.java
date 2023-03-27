package com.sinfor.stater.net.threadfactory;

import com.sinfor.stater.net.exception.ThreadPoolFullException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author fengwen
 * @date 2022/5/10
 * @description 处理业务逻辑的线程池
 **/
@Slf4j
public class BizThreadFactory {

    public ThreadPoolExecutor bizPool;

    private static final BizThreadFactory INSTANCE = new BizThreadFactory();

    private BizThreadFactory() {
        log.info("----调用biz thread factory");
        bizPool = biz();
    }

    public static BizThreadFactory getInstance() {
        return INSTANCE;
    }

    public ThreadPoolExecutor biz() {
        return new ThreadPoolExecutor(1,
                1,
                600,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "sinfor-biz-thread");
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        throw new ThreadPoolFullException("当前服务器人数已超载，请稍后再操作...");
                    }
                });
    }
}
