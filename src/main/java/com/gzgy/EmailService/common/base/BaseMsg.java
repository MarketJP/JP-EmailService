package com.gzgy.EmailService.common.base;

import com.gzgy.EmailService.common.enums.ResultCode;
import com.gzgy.EmailService.common.exception.ProjectBusinessBaseException;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回数据
 */
public class BaseMsg<T> {
    @ApiModelProperty(value = "返回code")
    private int code;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "回传对象数据")
    private T data;

    protected BaseMsg() {
    }

    public BaseMsg(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> BaseMsg<T> success(T data) {
        return new BaseMsg<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> BaseMsg<T> success(T data, String message) {
        return new BaseMsg<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功返回的信息
     * @param message
     * @param <T>
     * @return
     */
    public static <T> BaseMsg<T> success(String message){
        return new BaseMsg<T>(ResultCode.SUCCESS.getCode(), message, null);
    }

    /**
     * 返回失败的结果
     * @param e 自定义异常
     * @return
     */
    public static <T> BaseMsg<T> failed(ProjectBusinessBaseException e){
        return new BaseMsg<T>(e.getStatus(), e.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> BaseMsg<T> failed(ResultCode errorCode) {
        return new BaseMsg<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> BaseMsg<T> failed(String message) {
        return new BaseMsg<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> BaseMsg<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> BaseMsg<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> BaseMsg<T> validateFailed(String message) {
        return new BaseMsg<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> BaseMsg<T> unauthorized(T data) {
        return new BaseMsg<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> BaseMsg<T> forbidden(T data) {
        return new BaseMsg<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
