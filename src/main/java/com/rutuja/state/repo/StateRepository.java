package com.rutuja.state.repo;

import com.rutuja.state.model.StateModel;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface StateRepository extends ReactiveCrudRepository<StateModel,Integer> {
    @Modifying
    @Query("UPDATE state SET stateName = :stateName WHERE stateId = :stateId")
    public  Mono<Integer> updateState( String stateName,Integer stateId);


    public Mono<StateModel> findBystateName(String stateName);

    @Query("SELECT * FROM state WHERE countryid = :countryId")
    public Flux<StateModel> findBycountryId(Integer countryId);

    @Modifying
    @Query("UPDATE state SET action = 'delete' WHERE stateId = :stateId")
    public Mono<Integer> updateByAction(Integer stateId);

    @Query("SELECT * FROM state WHERE action = 'active'")
    public Flux<StateModel> findAllByAction();

    @Query("SELECT * FROM state WHERE action = 'active' AND stateId IN (:stateIds)")
    public Flux<StateModel> findAllByAction(List<Integer> stateIds);

    @Query("SELECT * FROM state WHERE action = 'active' AND stateId = :stateId")
    public Mono<StateModel> findBystateId(Integer stateId);
}
