package com.zhenxin.chamberlain.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author xzhen
 * @created 17:07 26/02/2019
 * @description TODO
 */
@Slf4j
@Configuration
public class EsConfig {

    @Value("${elasticsearch-nodes}")
    private String esNodes;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder;
        try {
            builder = this.getBuilder();
        } catch (RuntimeException e) {
            throw new RuntimeException("ElasticsearchRestHighLevelClient Initialization fail: " + e.getMessage());
        }

        RestHighLevelClient client = new RestHighLevelClient(builder);
        this.checkConnect(client);
        return client;
    }

    private RestClientBuilder getBuilder() throws RuntimeException {
        if (StringUtils.isEmpty(this.esNodes)) {
            throw new RuntimeException("properties elasticsearch-nodes is empty.");
        }
        String[] nodesStr = StringUtils.commaDelimitedListToStringArray(this.esNodes);
        HttpHost[] httpHosts = Arrays.stream(nodesStr).map(item -> {
            if (StringUtils.isEmpty(item)) {
                throw new RuntimeException("\"" + this.esNodes + "\" format error.");
            }
            return HttpHost.create(item.trim());
        }).toArray(HttpHost[]::new);
        return RestClient.builder(httpHosts);
    }

    private void checkConnect(RestHighLevelClient client) {
        try {
            boolean flag = client.ping(RequestOptions.DEFAULT);
            if (flag) {
                log.info("ElasticsearchRestHighLevelClient Initialization success.");
                return;
            }
        } catch (Exception ignored) {
        }
        throw new RuntimeException("ElasticsearchRestHighLevelClient Initialization fail: connection fail.");
    }
}
