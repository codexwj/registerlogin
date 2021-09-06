package com.codejames.registerlogin.common.exception;

public class AcException extends Exception{

    private static final long serialVersionUID = -5062390266059090468L;

    private String code;
    private String message;
    private Throwable cause;
    private ExceptionCodeInterface exceptionCode;

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

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public ExceptionCodeInterface getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(ExceptionCodeInterface exceptionCode) {
        this.exceptionCode = exceptionCode;
    }


    public AcException(ExceptionCodeInterface code, Throwable cause){
        super(String.format("%s: %s", code.getCode(), code.getMessage()),cause);
        this.exceptionCode = code;
        this.code = code.getCode();
        this.message = code.getMessage();
        this.cause = cause;
    }

    public AcException(ExceptionCodeInterface code, String message, Throwable cause) {
        super(String.format("%s：%s(%s)", code.getCode(), code.getMessage(), message), cause);
        this.exceptionCode = code;
        this.code = code.getCode();
        this.message = code.getMessage() + "(" + message + ")";
        this.cause = cause;
    }

    public AcException(ExceptionCodeInterface code, String message) {
        super(String.format("%s：%s(%s)", code.getCode(), code.getMessage(), message));
        this.exceptionCode = code;
        this.code = code.getCode();
        this.message = code.getMessage() + "(" + message + ")";
    }

    public AcException(String code, String message) {
        super(String.format("%s：%s", code, message));
        this.code = code;
        this.message = message;
    }

    public AcException(ExceptionCodeInterface code) {
        super(String.format("%s：%s", code.getCode(), code.getMessage()));
        this.exceptionCode = code;
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
