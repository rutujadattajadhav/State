package com.rutuja.state.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "state")
@Data
public class StateModel {
    @Column(name = "stateId")
    @Id
   private Integer stateId;

    @Column(name = "stateName")
    private String stateName;
}
