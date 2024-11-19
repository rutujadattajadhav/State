package com.rutuja.state.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "state")
@Data
public class StateModel {
    @Column(value = "stateId")
    @Id
   private Integer stateId;//stateName

    @Column(value = "stateName")
    private String stateName;//stateName

    @Column(value = "countryid")
    private Integer countryId;

    @Column(value = "action")
    private String action;
}
