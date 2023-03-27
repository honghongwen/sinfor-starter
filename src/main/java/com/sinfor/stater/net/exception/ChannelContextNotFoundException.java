package com.sinfor.stater.net.exception;

import lombok.Data;

/**
 * @author fengwen
 * @date 2022/4/29
 * @description TODO
 **/
@Data
public class ChannelContextNotFoundException extends RuntimeException {

    public ChannelContextNotFoundException(String message) {
        super(message);
    }
}
