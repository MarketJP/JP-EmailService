package com.gzgy.EmailService.common.enums;

/**
 * API操作码
 */
public enum ResultCode  {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),

    UNAUTHORIZED(401, "用户暂未登录"),
    FORBIDDEN(403, "没有相关权限"),
    VALIDATE_FAILED(404, "参数检验失败"),
    REQUEST_FREQUENTLY(405, "请求过于频繁,超出限制"),
    AUTHORIZED_UNBIND(406, "未授权绑定"),

    UNKNOWN(9999, "系统未知错误");
    private int code;
    private String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
