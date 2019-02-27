package com.zhenxin.chamberlain.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhenxin.chamberlain.dto.search.IndexTemplate;
import com.zhenxin.chamberlain.service.ConsumeService;
import com.zhenxin.chamberlain.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author xzhen
 * @created 19:12 26/02/2019
 * @description TODO
 */
@Slf4j
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    public static final String INDEX_NAME = "chamberlain";

    public static final String INDEX_TYPE = "_doc";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ConsumeService consumeService;

    @Override
    public void index(IndexTemplate template) {
        String string = JSON.toJSONString(template);
        log.info("index source: {}", string);
        IndexRequest indexRequest = new IndexRequest()
                .index(INDEX_NAME)
                .type(INDEX_TYPE)
                .id(template.getConsumeId())
                .source(string, XContentType.JSON);

        IndexResponse response;
        try {
            response = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (RestStatus.CREATED.equals(response.status())) {
            log.info("create source success!");
        }
        else if (RestStatus.OK.equals(response.status())) {
            log.info("update source success!");
        }
    }

    @Override
    public void remove(IndexTemplate template) {
        DeleteRequest deleteRequest = new DeleteRequest()
                .index(INDEX_NAME)
                .type(INDEX_TYPE)
                .id(template.getConsumeId());

        DeleteResponse response;
        try {
            response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (RestStatus.OK.equals(response.status())) {
            log.info("delete source success!");
        }
    }
}
