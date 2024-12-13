package com.rutuja.state.exception;


import com.rutuja.state.error.SError;
import lombok.Data;
import java.util.List;


@Data
public class ServiceException extends Exception {
List<SError> errorMessage;


    public ServiceException(List<SError> errorList) {
        this.errorMessage=errorList;
    }


    public List<SError> getErrorMessage() {
        return errorMessage;
    }
}
