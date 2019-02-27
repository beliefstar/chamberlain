package com.zhenxin.chamberlain.dto.search;

import lombok.Data;

import java.util.Date;

/**
 * @author xzhen
 * @created 9:57 27/02/2019
 * @description TODO
 */
@Data
public class IndexTemplate {

    private String consumeId;

    /**
     * 标题
     */
    private String title;

    /**
     * 金额
     */
    private String amount;

    /**
     * 发生时间
     */
    private Date happenDate;
}
