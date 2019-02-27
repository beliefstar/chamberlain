package com.zhenxin.chamberlain.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhenxin.chamberlain.common.utils.BeanUtils;
import com.zhenxin.chamberlain.dao.pojo.Consume;
import com.zhenxin.chamberlain.dto.search.IndexKey;
import com.zhenxin.chamberlain.dto.search.IndexMessage;
import com.zhenxin.chamberlain.dto.search.IndexTemplate;
import com.zhenxin.chamberlain.service.ConsumeService;
import com.zhenxin.chamberlain.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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
    public boolean index(IndexMessage indexMessage) {
        Consume consume = consumeService.findById(indexMessage.getConsumeId());
        if (consume == null || consume.getDelFlg()) {
            return true;
        }
        IndexTemplate template = new IndexTemplate();
        BeanUtils.copyProperties(consume, template);
        SearchResponse response;
        try {
            response = findByConsumeId(template.getConsumeId());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        boolean success;
        if (response.getHits().totalHits == 0) {
            success = create(template);
        } else if (response.getHits().totalHits == 1) {
            String esId = response.getHits().getAt(0).getId();
            success = update(esId, template);
        } else {
            success = removeAndCreate(response.getHits().totalHits, template);
        }
        return success;
    }

    @Override
    public boolean remove(IndexMessage indexMessage) {
        long n = deleteDocByIndex(indexMessage.getConsumeId());
        if (n > 0) {
            log.info("delete success! count: {}", n);
        }
        return true;
    }

    private SearchResponse findByConsumeId(String consumeId) throws IOException {
        SearchRequest searchRequest = new SearchRequest(
                new String[]{INDEX_NAME},
                new SearchSourceBuilder()
                        .query(QueryBuilders.termQuery(IndexKey.CONSUME_ID, consumeId)));
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    private boolean create(IndexTemplate template) {
        String string = JSON.toJSONString(template);
        log.info("create source: {}", string);
        IndexRequest indexRequest = new IndexRequest()
                .index(INDEX_NAME)
                .type(INDEX_TYPE)
                .source(string, XContentType.JSON);

        IndexResponse response;
        try {
            response = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (RestStatus.CREATED.equals(response.status())) {
            log.info("create source success!");
            return true;
        }
        return false;
    }

    private boolean update(String esId, IndexTemplate template) {
        String string = JSON.toJSONString(template);
        log.info("update source: {}", string);
        UpdateRequest indexRequest = new UpdateRequest()
                .index(INDEX_NAME)
                .type(INDEX_TYPE)
                .id(esId)
                .doc(string, XContentType.JSON);

        UpdateResponse response;
        try {
            response = client.update(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (RestStatus.OK.equals(response.status())) {
            log.info("update source success!");
            return true;
        }
        return false;
    }

    private boolean removeAndCreate(long needDelete, IndexTemplate template) {
        long removeNum = deleteDocByIndex(template.getConsumeId());
        if (removeNum != needDelete) {
            log.error("need delete count: {}, but real delete count: {}", needDelete, removeNum);
        }
        return create(template);
    }

    private long deleteDocByIndex(String consumeId) {
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest();
        deleteByQueryRequest.setConflicts("proceed");
        deleteByQueryRequest.setRefresh(true);
        deleteByQueryRequest.indices(INDEX_NAME);
        deleteByQueryRequest.types(INDEX_TYPE);
        deleteByQueryRequest.setQuery(QueryBuilders.termQuery(IndexKey.CONSUME_ID, consumeId));

        BulkByScrollResponse response;
        try {
            response = client.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        long deleted = response.getDeleted();
        log.info("delete count: {}", deleted);
        return deleted;
    }



}
