package com.rutuja.state.exception;

import com.rutuja.state.error.SError;

import java.util.List;

public class ValidationException extends Exception{
    List<SError> errorList;

    public ValidationException(List<SError> errorList) {
        this.errorList = errorList;
    }

    public List<SError> getErrorList() {
        return errorList;
    }
}
