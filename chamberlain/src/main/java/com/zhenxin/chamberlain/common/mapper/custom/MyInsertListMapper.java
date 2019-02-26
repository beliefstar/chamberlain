package com.zhenxin.chamberlain.common.mapper.custom;

import com.zhenxin.chamberlain.common.mapper.provider.MyProvider;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * @author xzhen
 * @created 14:18 26/02/2019
 * @description TODO
 */
@RegisterMapper
public interface MyInsertListMapper<T> {
    @InsertProvider(
            type = MyProvider.class,
            method = "dynamicSQL"
    )
    int insertList(List<T> var1);
}
