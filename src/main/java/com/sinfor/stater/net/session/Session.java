package com.sinfor.stater.net.session;

import com.sinfor.stater.net.domain.Request;
import com.sinfor.stater.net.domain.Response;
import com.sinfor.stater.net.exception.BusinessException;
import lombok.Data;

/**
 * @author fengwen
 * @date 2022/5/17
 * @description TODO
 **/
@Data
public class Session {


    public static final ThreadLocal<Session> SESSION_CONTEXT = new ThreadLocal<>();


    protected SessionUser sessionUser;


    /**
     * 重要说明： 由于request在全局使用，所以非线程安全; 1.客户端需保证请求一个一个进行;
     * 2.服务器端推送、广播需保存不影响覆盖当前全局request对象; 若要解决线程安全问题：需把request作为变量传入调用函数中;
     * 但减少了程序开发简便性和简洁性
     */
    protected Request request;

    /**
     * 当前是否有耗时任务在执行
     */
    private boolean taskRuning;
    /**
     * 跟踪当前账户执行的SQL
     */
    private boolean traceAccount = false;

    /**
     * 此函数仅用于消息推送
     *
     * @param response
     */
    public void push(Response response, int module, short cmd) {
        throw new BusinessException("当前Session不是XfSocketSession;不支持push");
    }

    /**
     * 此函数用于远程接口调用
     *
     * @param response
     * @throws Exception
     */
    public void write(Response response) {
        throw new BusinessException("当前Session不是XfSocketSession;不支持write");
    }

}
