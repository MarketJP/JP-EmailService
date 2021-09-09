package com.gzgy.EmailService.common.exception;


import com.alibaba.fastjson.JSON;
import com.gzgy.EmailService.common.base.BaseMsg;
import com.gzgy.EmailService.common.enums.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义异常
     */
    @ExceptionHandler(ProjectBusinessBaseException.class)
    public BaseMsg handleIDoctorBusinessBaseException(ProjectBusinessBaseException e) {
        logger.error(e.getMessage());
        return BaseMsg.failed(e);
    }

    /**
     * 参数验证异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public BaseMsg dataInvalidExceptionHandler(Exception ex) {
        List<DataInvalidResult> results = new ArrayList<>();
        DataInvalidResult dataInvalidResult = null;
        BindingResult bindingResult = null;
        if (ex instanceof BindException) {
            bindingResult = ((BindException) ex).getBindingResult();
        } else if (ex instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
        }
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : bindingResult.getFieldErrors()) {
            dataInvalidResult = new DataInvalidResult();
            dataInvalidResult.setDefaultMessage(error.getDefaultMessage());
            dataInvalidResult.setField(error.getField());
            dataInvalidResult.setRejectedValue(error.getRejectedValue());
            results.add(dataInvalidResult);
        }
        logger.error("全局参数异常：{}信息", JSON.toJSONString(results));
        return BaseMsg.failed(results.get(0).getDefaultMessage());
    }

    /**
     * 默认异常
     */
    @ExceptionHandler(value = Exception.class)
    public BaseMsg defaultErrorHandler(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return BaseMsg.failed(ResultCode.UNKNOWN);
    }

}
