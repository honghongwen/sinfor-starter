package com.sinfor.stater.net.domain;

import com.sinfor.stater.net.processor.DataProcessor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author fengwen
 * @date 12:01 2022/4/27
 * @description 通用返回 基本用法
 * <p>
 * Result.getInstance(Request).compressing(false).ok(data);
 **/
@Slf4j
@Data
@ToString
public class Response {

    private short sequence;
    private int module;
    private short cmd;
    private boolean compress;
    private Object data;
    private int stateCode;
    private byte[] sign;
    private byte[] jMeter;


    private Response() {

    }

    /**
     * 不调用时默认为false
     *
     * @param compress
     * @return
     */
    public Response compressing(boolean compress) {
        this.setCompress(compress);
        return this;
    }

    public Response ok(Object data) {
        log.debug("[response data]：{}", data);
        this.setData(data);
        this.setStateCode(0);
        return this;
    }

    public Response code(Integer code) {
        this.setStateCode(code);
        return this;
    }

    public Response fail(Object data) {
        log.debug("[response data]：{}", data);
        this.setData(data);
        this.setStateCode(1009);
        return this;
    }

    public Response encrypt() {
        try {
            DataProcessor.encodeResponse(this, false);
        } catch (Exception e) {
            log.error("返回数据加密出错", e);
        }
        return this;
    }

    public Response sequence(Short sequence) {
        this.setSequence(sequence);
        return this;
    }

     /*旧系统遗留的问题，对接时可能根据"\n".getBytes()来判断是不是最后的数据包了。
     不清楚具体情况，结尾没有"\n".getBytes()客户端不能正常解析数据。*/
    public Response jMeter(byte[] bytes) {
        this.setJMeter(bytes);
        return this;
    }

    /**
     * 获取instance方法
     *
     * @param request
     * @return
     */
    public static Response getInstance(Request request) {
        Response response = new Response();
        response.setCmd((short) request.getCmd());
        response.setModule(request.getModule());
        response.setSequence(Short.MAX_VALUE);
        response.setJMeter("\n".getBytes(StandardCharsets.UTF_8));
        return response;
    }
}
