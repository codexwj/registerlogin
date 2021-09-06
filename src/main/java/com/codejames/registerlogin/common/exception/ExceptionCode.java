package com.codejames.registerlogin.common.exception;

public enum ExceptionCode implements ExceptionCodeInterface{
    NO_FILE("8001","缺少文件"),
    FILE_TYPE_ERROR("8002", "非法的文件类型"),
    FILE_SIZE_TOO_LARGE("8003", "文件大小超过限制"),
    BAD_REQUEST("8004", "错误的请求"),
    INVAILED_PARAMS("8005", "参数错误"),
    REGION_NOTFOUND("8100", "用户未设置地区"),
    INVALID_SIGN("8200", "签名不正确");

    private String code;
    private String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
