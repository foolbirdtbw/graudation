package com.bowen.common;

import com.bowen.exception.ExceptionCodeEnum;

/**
 * @author bowen
 */
public class Result<T> {
    private T data;
    private Integer code;
    private String message;

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    private Result(ExceptionCodeEnum e){
        this.message = e.getDesc();
        this.code = e.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private Result(String message){
        this.message = message;
    }

    public static <T> Result<T> error(ExceptionCodeEnum e){
        return new Result<>(e);
    }

    public static Result<?> error(ExceptionCodeEnum e,Object data){
        return new Result<>(e).setData(data);
    }

    public static <T> Result<T> success(){
        return new Result<>(ExceptionCodeEnum.SUCCESS);
    }

    public static Result success(Object data){
        return new Result<>(ExceptionCodeEnum.SUCCESS).setData(data);
    }



}
