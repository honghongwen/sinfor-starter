package com.sinfor.stater.net.exports.service;

/**
 * @author fengwen
 * @date 17:16 2022/4/27
 * @description 提供给引用包实现的spi，BizHandler会调用spi中方法,spi需服务提供方具体实现
 **/
public interface HandlerService {


    /**
     * 执行业务前
     */
    void before();

    /**
     * 执行业务
     *
     * @return 返回结果
     */
    Object execute();

    /**
     * 执行业务后
     * @param response
     */
    void after(Object response);

}
