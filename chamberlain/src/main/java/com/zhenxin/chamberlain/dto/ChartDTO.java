package com.zhenxin.chamberlain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author xzhen
 * @created 15:49 26/02/2019
 * @description TODO
 */
@Data
public class ChartDTO {

    private String title;

    private Double value;

    private Date date;

}
