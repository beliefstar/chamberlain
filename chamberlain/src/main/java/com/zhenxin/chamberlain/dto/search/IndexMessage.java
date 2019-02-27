package com.zhenxin.chamberlain.dto.search;

import lombok.Data;

/**
 * @author xzhen
 * @created 20:07 26/02/2019
 * @description TODO
 */
@Data
public class IndexMessage {

    public static final String INDEX = "index";
    public static final String REMOVE = "remove";


    private String consumeId;

    private String operation;

    private int retry;
}
