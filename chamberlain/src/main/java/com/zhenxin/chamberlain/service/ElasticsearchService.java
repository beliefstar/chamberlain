package com.zhenxin.chamberlain.service;

import com.zhenxin.chamberlain.dto.search.IndexMessage;

/**
 * @author xzhen
 * @created 19:11 26/02/2019
 * @description TODO
 */
public interface ElasticsearchService {

    boolean index(IndexMessage indexMessage);


    boolean remove(IndexMessage indexMessage);


}
