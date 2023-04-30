package com.bowen.exception;

/**
 * @author bowen
 */

import com.bowen.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author bowen
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    /**
     * 业务异常
     *
     * @param
     * @return
     */
    @ExceptionHandler(BizException.class)
    public Result<ExceptionCodeEnum> handleBizException(BizException bizException) {
        log.warn("业务异常:{}", bizException.getError().getDesc(), bizException);
        return Result.error(bizException.getError());
    }

    /**
     * 运行时异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<ExceptionCodeEnum> handleRunTimeException(RuntimeException e) {
        log.warn("运行时异常: {}", e.getMessage(), e);
        return Result.error(ExceptionCodeEnum.ERROR);
    }

}
