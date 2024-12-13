package com.rutuja.state.error;

import lombok.ToString;


@ToString
public class SError {
    private String errorMsg;
    private String errorKey;

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public SError(String errorMsg, String errorKey) {
        this.errorMsg = errorMsg;
        this.errorKey = errorKey;
    }
}
