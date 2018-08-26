package com.mbakgun.spring.util.exception;

public class RequiredFieldException extends MbaException {
    private static final long serialVersionUID = 1L;

    public RequiredFieldException(String msgKey, Object... args) {
        super(MbakgunError.EC_NULL_OR_EMPTY, msgKey, args);
    }

    public RequiredFieldException(String msgKey, Throwable cause, Object... args) {
        super(MbakgunError.EC_NULL_OR_EMPTY, msgKey, cause, args);
    }
}