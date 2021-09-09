package com.gzgy.EmailService.common.exception;

import com.gzgy.EmailService.common.enums.ResultCode;

/**
 * 自定义业务异常
 */
public class ProjectBusinessBaseException extends RuntimeException{

    private final static  long serialVersionUID = -1;

    private int status = 500;   //默认500

    public ProjectBusinessBaseException(ResultCode code) {
        super(code.getMessage());
        this.status = code.getCode();
    }

    public ProjectBusinessBaseException(String message) {
        super(message);
    }

    public ProjectBusinessBaseException(String message, int status) {
        super(message);
        this.status=status;
    }

    public ProjectBusinessBaseException(String message , Throwable casuse , int status) {
        super(message,casuse);
        this.status = status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static final ProjectBusinessBaseException fail(String message){
        return  new ProjectBusinessBaseException(message);
    }

    public static final ProjectBusinessBaseException fail(int status , String message){
        ProjectBusinessBaseException exception = new ProjectBusinessBaseException(message);
        exception.setStatus(status);
        return exception;
    }

}
