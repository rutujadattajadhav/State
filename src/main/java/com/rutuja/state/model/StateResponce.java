package com.rutuja.state.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
@Data
public class StateResponce {

    private Integer stateId;//stateName
    private String stateName;//stateName
    private Country country;
    private String action;
}
