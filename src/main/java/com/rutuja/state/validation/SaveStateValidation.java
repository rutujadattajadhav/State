package com.rutuja.state.validation;

import com.rutuja.state.error.SError;
import com.rutuja.state.exception.ValidationException;
import com.rutuja.state.model.StateModel;
import com.rutuja.state.model.StateRequestBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class SaveStateValidation {

  public void validate(StateRequestBean stateModel) throws ValidationException {
      List<SError> errorList = new ArrayList<>();
      if(Objects.isNull(stateModel)){
          errorList.add(new SError("Object is null","300"));
      }else{
         if(StringUtils.isEmpty(stateModel.getStateName())){
             errorList.add(new SError("state name is null","304"));
         }
         if(StringUtils.isEmpty(stateModel.getCountry().getCountryId())){
             errorList.add(new SError("country Id is null","304"));
         }
      }
    if(!errorList.isEmpty() ){
        throw new ValidationException(errorList);
    }

    }
}
