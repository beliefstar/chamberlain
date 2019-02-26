package com.zhenxin.chamberlain.common.mapper;


import com.zhenxin.chamberlain.common.mapper.custom.MyInsertListMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author xzhen
 * @created 13:57 26/02/2019
 * @description TODO
 */
public interface BaseMapper<T> extends Mapper<T>, ConditionMapper<T>, IdsMapper<T>, MyInsertListMapper<T> {
}
