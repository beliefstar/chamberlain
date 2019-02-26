package com.zhenxin.chamberlain.service.impl;

import com.zhenxin.chamberlain.common.utils.BeanUtils;
import com.zhenxin.chamberlain.common.utils.KeyGenerator;
import com.zhenxin.chamberlain.dao.mapper.ConsumeMapper;
import com.zhenxin.chamberlain.dao.pojo.Consume;
import com.zhenxin.chamberlain.service.ConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xzhen
 * @created 14:32 26/02/2019
 * @description TODO
 */
@Service
public class ConsumeServiceImpl implements ConsumeService {

    @Autowired
    private ConsumeMapper consumeMapper;


    @Override
    public Consume save(Consume consume) {
        consume.setConsumeId(KeyGenerator.gain());
        BeanUtils.initBean(consume);
        consumeMapper.insert(consume);
        return consume;
    }

    @Override
    public void delete(String consumeId) {
        consumeMapper.deleteByPrimaryKey(consumeId);
    }

    @Override
    public Consume update(Consume consume) {
        consumeMapper.updateByPrimaryKeySelective(consume);
        return consume;
    }

    @Override
    public List<Consume> select(Consume consume) {
        return consumeMapper.select(consume);
    }
}
