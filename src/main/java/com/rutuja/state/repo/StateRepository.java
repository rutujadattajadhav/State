package com.rutuja.state.repo;

import com.rutuja.state.model.StateModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CrudRepository<StateModel,Integer> {
}
