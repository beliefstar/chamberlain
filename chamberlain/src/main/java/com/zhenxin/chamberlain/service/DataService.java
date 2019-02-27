package com.zhenxin.chamberlain.service;

import com.zhenxin.chamberlain.dto.ChartDTO;

import java.util.List;

/**
 * @author xzhen
 * @created 15:48 26/02/2019
 * @description TODO
 */
public interface DataService {

    List<ChartDTO> getDaysData(int days);
}
