package com.zhenxin.chamberlain.service;

import com.zhenxin.chamberlain.dto.search.IndexTemplate;

/**
 * @author xzhen
 * @created 19:11 26/02/2019
 * @description TODO
 */
public interface ElasticsearchService {

    void index(IndexTemplate indexTemplate);


    void remove(IndexTemplate indexTemplate);


}
