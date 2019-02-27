package com.zhenxin.chamberlain.service.impl;

import com.zhenxin.chamberlain.common.MQKeys;
import com.zhenxin.chamberlain.common.exception.ServiceException;
import com.zhenxin.chamberlain.common.utils.BeanUtils;
import com.zhenxin.chamberlain.common.utils.KeyGenerator;
import com.zhenxin.chamberlain.dao.mapper.ConsumeMapper;
import com.zhenxin.chamberlain.dao.pojo.Consume;
import com.zhenxin.chamberlain.dto.ConsumeQueryDTO;
import com.zhenxin.chamberlain.dto.search.IndexMessage;
import com.zhenxin.chamberlain.service.ConsumeService;
import com.zx.rabbit.common.model.Message;
import com.zx.rabbit.produce.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

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

    @Autowired
    private Producer producer;

    @Override
    public Consume save(Consume consume) {
        consume.setConsumeId(KeyGenerator.gain());
        BeanUtils.initBean(consume);
        consumeMapper.insert(consume);
        sendMQ(consume, IndexMessage.INDEX);
        return consume;
    }

    private void sendMQ(Consume consume, String operation) {
        IndexMessage indexMessage = new IndexMessage();
        indexMessage.setConsumeId(consume.getConsumeId());
        indexMessage.setOperation(operation);
        indexMessage.setRetry(2);
        Message message = new Message();
        message.setData(indexMessage);
        message.setExchange(MQKeys.INDEX_TOPIC_NAME);
        message.setRouteKey(MQKeys.INDEX_ROUTE_KEY);
        producer.send(message);
    }

    @Override
    public void delete(String consumeId) {
        Consume consume = consumeMapper.selectByPrimaryKey(consumeId);
        if (consume != null && !consume.getDelFlg()) {
            consumeMapper.deleteByPrimaryKey(consumeId);
            sendMQ(consume, IndexMessage.REMOVE);
        }
    }

    @Override
    public Consume update(Consume consume) {
        Assert.notNull(consume.getConsumeId(), "consumeId");
        Consume t = consumeMapper.selectByPrimaryKey(consume.getConsumeId());
        if (t != null && !t.getDelFlg()) {
            consumeMapper.updateByPrimaryKeySelective(consume);
            sendMQ(consume, IndexMessage.INDEX);
            return consume;
        }
        throw new ServiceException("数据不存在");
    }

    @Override
    public List<Consume> select(ConsumeQueryDTO consume) {
        Condition condition = new Condition(Consume.class);
        Example.Criteria criteria = condition.createCriteria();

        if (!StringUtils.isEmpty(consume.getConsumeId())) {
            criteria.andEqualTo("consumeId", consume.getConsumeId());
        }
        if (!StringUtils.isEmpty(consume.getTitle())) {
            criteria.andLike("title", "%" + consume.getTitle() + "%");
        }
        if (consume.getAmountLeft() != null && consume.getAmountRight() != null) {
            criteria.andBetween("amount", consume.getAmountLeft(), consume.getAmountRight());
        } else if (consume.getAmountLeft() != null) {
            criteria.andGreaterThanOrEqualTo("amount", consume.getAmountLeft());
        } else if (consume.getAmountRight() != null) {
            criteria.andLessThanOrEqualTo("amount", consume.getAmountRight());
        }
        if (consume.getHappenDateLeft() != null && consume.getHappenDateRight() != null) {
            criteria.andBetween("happenDate", consume.getHappenDateLeft(), consume.getHappenDateRight());
        } else if (consume.getHappenDateLeft() != null) {
            criteria.andGreaterThanOrEqualTo("happenDate", consume.getHappenDateLeft());
        } else if (consume.getHappenDateRight() != null) {
            criteria.andLessThanOrEqualTo("happenDate", consume.getHappenDateRight());
        }
        criteria.andEqualTo("delFlg", false);

        return consumeMapper.selectByCondition(condition);
    }

    @Override
    public Consume findById(String consumeId) {
        return consumeMapper.selectByPrimaryKey(consumeId);
    }
}
