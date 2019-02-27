package com.zhenxin.chamberlain.service.impl;

import com.zhenxin.chamberlain.dao.pojo.Consume;
import com.zhenxin.chamberlain.dto.ChartDTO;
import com.zhenxin.chamberlain.dto.ConsumeQueryDTO;
import com.zhenxin.chamberlain.service.ConsumeService;
import com.zhenxin.chamberlain.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * @author xzhen
 * @created 15:51 26/02/2019
 * @description TODO
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private ConsumeService consumeService;

    @Override
    public List<ChartDTO> getDaysData(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -days);

        ConsumeQueryDTO queryDTO = new ConsumeQueryDTO();
        queryDTO.setHappenDateLeft(calendar.getTime());


        return null;
    }

    private ChartDTO convert(Consume consume) {
        ChartDTO dto = new ChartDTO();
        dto.setDate(consume.getHappenDate());
        dto.setTitle(consume.getTitle());
        dto.setValue(Double.parseDouble(consume.getAmount()));
        return dto;
    }

}
