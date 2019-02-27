package com.zhenxin.chamberlain.handler;

import com.zhenxin.chamberlain.common.exception.ServiceException;
import com.zhenxin.chamberlain.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xzhen
 * @created 20:29 26/02/2019
 * @description TODO
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public Result serviceException(ServiceException e) {
        log.error("--ServiceExceptionHandler--msg: {}", e.getMessage());
        return Result.error(e.getMessage());
    }
}
