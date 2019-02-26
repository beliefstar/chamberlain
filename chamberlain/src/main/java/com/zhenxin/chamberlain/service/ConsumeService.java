package com.zhenxin.chamberlain.service;

import com.zhenxin.chamberlain.dao.pojo.Consume;

import java.util.List;

/**
 * @author xzhen
 * @created 14:31 26/02/2019
 * @description TODO
 */
public interface ConsumeService {

    Consume save(Consume consume);

    void delete(String consumeId);

    Consume update(Consume consume);

    List<Consume> select(Consume consume);
}
