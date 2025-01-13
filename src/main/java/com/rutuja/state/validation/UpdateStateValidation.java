package com.rutuja.state.validation;


import com.rutuja.state.error.SError;
import com.rutuja.state.exception.ValidationException;
import com.rutuja.state.model.StateRequestBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UpdateStateValidation {
    public void validate(StateRequestBean stateRequestBean) throws ValidationException {
        List<SError> errorList=new ArrayList<>();
        if(Objects.isNull(stateRequestBean)){
            errorList.add(new SError("object is null","300"));
        }else{
          if(StringUtils.isEmpty(stateRequestBean.getStateId())){
              errorList.add(new SError("Id is null","301"));
          }
          if(StringUtils.isEmpty(stateRequestBean.getStateName())){
              errorList.add(new SError("StateName is null","301"));
          }
            if(StringUtils.isEmpty(stateRequestBean.getCountry().getCountryId())){
                errorList.add(new SError("countryId  is null","301"));
            }
            if(StringUtils.isEmpty(stateRequestBean.getAction())){
                errorList.add(new SError("action is null","301"));
            }
            if(!errorList.isEmpty()){
                throw new ValidationException(errorList);
            }
        }
    }
}
