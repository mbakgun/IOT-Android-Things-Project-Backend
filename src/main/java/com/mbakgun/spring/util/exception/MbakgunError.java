package com.mbakgun.spring.util.exception;

public enum MbakgunError {

    INVALID_FIELD(2002),
    EC_NULL_OR_EMPTY(2003);

    private final int errorCode;

    MbakgunError(int errorCode) {
	this.errorCode = errorCode;
    }

    public int getValue() {
	return errorCode;
    }

    public int getErrorCode() {
	return errorCode;
    }
}