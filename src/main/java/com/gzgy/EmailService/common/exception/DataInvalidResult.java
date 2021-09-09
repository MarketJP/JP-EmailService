package com.gzgy.EmailService.common.exception;

/**
 * 参数验证错误封装对象
 */
public class DataInvalidResult {
    private String field;
    private Object rejectedValue;
    private String defaultMessage;

    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public Object getRejectedValue() {
        return rejectedValue;
    }
    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }
    public String getDefaultMessage() {
        return defaultMessage;
    }
    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
