package com.zhenxin.chamberlain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xzhen
 * @created 16:05 26/02/2019
 * @description TODO
 */
@Data
public class ConsumeQueryDTO {

    private String consumeId;

    /**
     * 标题
     */
    private String title;

    /**
     * 金额
     */
    private BigDecimal amountLeft;

    /**
     * 金额
     */
    private BigDecimal amountRight;

    /**
     * 发生时间
     */
    private Date happenDateLeft;

    /**
     * 发生时间
     */
    private Date happenDateRight;

    private Boolean delFlg;
}
