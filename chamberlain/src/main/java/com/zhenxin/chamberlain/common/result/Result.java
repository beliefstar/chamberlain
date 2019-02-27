package com.zhenxin.chamberlain.common.result;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author xzhen
 * @created 20:29 26/02/2019
 * @description TODO
 */
@Data
public class Result {

    private Integer code;

    private String message;

    private Boolean success;

    private Object data;

    public Result(Integer code, String message, boolean success, Object data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public static Result ok() {
        return new Result(HttpStatus.OK.value(), "", true, null);
    }

    public static Result ok(Object data) {
        return new Result(HttpStatus.OK.value(), "", true, data);
    }

    public static Result ok(Integer code, Object data) {
        return new Result(code, "", true, data);
    }

    public static Result error(Integer code, String message) {
        return new Result(code, message, false, null);
    }

    public static Result error(String message) {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, false, null);
    }
}
