package com.rutuja.state.repo;

import com.rutuja.state.entity.SequenceEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SequenceRepository extends ReactiveCrudRepository<SequenceEntity,String> {


    @Query(value="select value from sequence where name= 'state'")
    public Mono<Integer> selectValue();


    @Modifying
    @Query(value="UPDATE sequence SET value =:sequecnceValue  WHERE name = 'state'")
    public Mono<Integer> updateSequenceState(Integer sequecnceValue);

}
