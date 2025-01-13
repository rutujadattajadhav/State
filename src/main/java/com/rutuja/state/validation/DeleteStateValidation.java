package com.rutuja.state.validation;

import com.rutuja.state.error.SError;
import com.rutuja.state.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DeleteStateValidation {

    public void validate(Integer  stateId) throws ValidationException {
        List<SError> errorList=new ArrayList<>();

        if(Objects.isNull(stateId)) {
            errorList.add(new SError("mob is  null", "2"));
        }

        if(!errorList.isEmpty()){
            throw new ValidationException(errorList);
        }
    }
}
