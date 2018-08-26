package com.mbakgun.spring.util.exception;

public class MbaException extends Exception {
    private static final long serialVersionUID = 1L;
    private int code;
    private Object[] args;

    public MbaException(MbakgunError error, String msgKey, Object... args) {
        super(msgKey);
        this.code = error.getErrorCode();
        this.args = args;
    }

    public MbaException(MbakgunError error, String msgKey, Throwable cause, Object... args) {
        super(msgKey, cause);
        this.code = error.getErrorCode();
        this.args = args;
    }

    public int getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }
}