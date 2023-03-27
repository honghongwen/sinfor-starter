package com.sinfor.stater.net.exception;

import lombok.Data;

/**
 * @author fengwen
 * @date 2022/4/29
 * @description TODO
 **/
@Data
public class RequestNotFoundException extends RuntimeException {

    public RequestNotFoundException(String message) {
        super(message);
    }
}
