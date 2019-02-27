package com.zhenxin.chamberlain.handler;

import com.zhenxin.chamberlain.dto.search.IndexMessage;
import com.zhenxin.chamberlain.service.ElasticsearchService;
import com.zx.rabbit.common.annotation.RabbitMQHandler;
import com.zx.rabbit.consume.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xzhen
 * @created 20:17 26/02/2019
 * @description TODO
 */
@Slf4j
@RabbitMQHandler("index")
public class MessageIndexHandler implements Consumer<IndexMessage> {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Override
    public boolean handle(IndexMessage indexMessage) {
        log.info("receive message -->>> {}", indexMessage);
        try {
            String operation = indexMessage.getOperation();
            switch (operation) {
                case IndexMessage.INDEX:
                    return elasticsearchService.index(indexMessage);
                case IndexMessage.REMOVE:
                    return elasticsearchService.remove(indexMessage);
                default:
                    log.error("未知的操作类型: {}", operation);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void handleRetryMax(IndexMessage indexMessage) {
        log.error("MQ retry count max: {}", indexMessage);
    }
}
