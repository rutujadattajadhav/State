package com.rutuja.state.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class StateRequestBean {

    private Integer stateId;
    private String stateName;
    private Country country;
    private String action;
}
