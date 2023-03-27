package com.sinfor.stater.net.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author fengwen
 */
@Data
@ToString
public class Request implements Serializable {

    private String version;
    private int sequence;
    private int module;
    private int cmd;
    private String sign;
    private boolean compress;
    private String data;
    private byte[] tempData;
}
