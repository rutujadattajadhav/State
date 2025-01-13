package com.rutuja.state.service;

import com.rutuja.state.error.SError;
import com.rutuja.state.exception.ServiceException;
import com.rutuja.state.model.Country;
import com.rutuja.state.model.StateModel;
import com.rutuja.state.model.StateRequestBean;
import com.rutuja.state.model.StateResponce;
import com.rutuja.state.repo.SequenceRepository;
import com.rutuja.state.repo.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateService {

    Logger log= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    @LoadBalanced
    private WebClient.Builder webClientBuilder;

    @Value("${server.servlet.context-path-country}")
    private String countryContextName;

//method=getStateById
//path=src/main/java/com/rutuja/state/service/StateService.java
//input=stateId
//output=Mono<StateModel>
 //calling the findBystateId method of stateRepository to get the state by stateId
 //if findBystateId method returns empty then return error message,
 //otherwise return the stateModel
    public Mono<StateModel> getStateById(Integer stateId) throws ServiceException {
       List<SError> errorList=new ArrayList<>();
        Mono<StateModel> modelMono=stateRepository.findBystateId(stateId);
        errorList.add(new SError("state not found","123"));
       return modelMono.switchIfEmpty(Mono.error(new ServiceException(errorList)));

    }

//method=getAllState
//path=src/main/java/com/rutuja/state/service/StateService.java
//input=none
//output=Flux<StateResponce>
//calling the findAllByAction method of stateRepository to get all the states
//if findAllByAction method returns empty then return error message,
//otherwise call flatMap method on stateModelFlux to get the stateModel
//then create a new StateResponce object and set the stateName, action, stateId and countryId of the stateModel
    //call Country API to get the country details by countryId
    //then call webClientBuilder.build() to get the WebClient.Builder object
    //then call get() method on the WebClient.Builder object to get the WebClient.RequestHeadersUriSpec object
    //then call uri() method on the WebClient.RequestHeadersUriSpec object to set the uri
    //then call headers() method on the WebClient.RequestHeadersUriSpec object to set the headers
    //then call retrieve() method on the WebClient.RequestHeadersUriSpec object to get the WebClient.ResponseSpec object
    //then call bodyToMono() method on the WebClient.ResponseSpec object to get the Mono<Country> object
    //then call onErrorResume() method on the Mono<Country> object to handle the error
    //then call map() method on the Mono<Country> object to get the country object
    //then set the countryName of the country object in the stateResponce object
    //then return the stateResponce object :map method return  is StateResponce
    public Flux<StateResponce> getAllState() {
        Flux<StateModel> stateModelFlux= stateRepository.findAllByAction();
        return stateModelFlux
                .flatMap(stateModel->{
                    StateResponce stateResponce   =new StateResponce();
                    stateResponce.setStateName(stateModel.getStateName());
                    stateResponce.setAction(stateModel.getAction());
                    stateResponce.setStateId(stateModel.getStateId());
                    stateResponce.setCountry(new Country());

                    return webClientBuilder.build()
                            .get()
                            .uri("http://COUNTRY" + countryContextName +"/getCountry/" + stateModel.getCountryId())
                            .headers(headers ->headers.setBasicAuth("countryUser", "countery"))
                            .retrieve()
                            .bodyToMono(Country.class)
                            .onErrorResume(e -> {
                                log.error("Failed to fetch country", e);
                                return Mono.empty();
                            })
                            .map(country -> {
                                stateResponce.getCountry().setCountryName(country.getCountryName());
                                return stateResponce;
                            });
                })
                .switchIfEmpty(Flux.error(new Exception("States not found")))
                .onErrorResume(error -> {
                    log.error("Error during state retrieval: ", error);
                    return Flux.error(new RuntimeException("Unable to fetch state details"));
                });
    }

//method=getStates
//path=src/main/java/com/rutuja/state/service/StateService.java
//input=stateIds
//output=Flux<StateModel>
//calling the findAllByAction method of stateRepository to get the states by stateIds
    //if findAllByAction method returns empty then call switchempty() and return error message,
    //otherwise return the stateModel
    public Flux<StateModel> getStates(List<Integer> stateIds) {
      return  stateRepository.findAllByAction(stateIds)
                .flatMap(state -> Mono.just(state))
                .switchIfEmpty(Flux.error(new Exception("States not found")))
                .onErrorResume(error -> {
                    log.error("Error during state retrieval: ", error);
                    return Flux.error(new RuntimeException("Unable to fetch state details"));
                });
    }

//method=updateState
//path=src/main/java/com/rutuja/state/service/StateService.java
//input=stateRequestBean
//output=Mono<String>
//create a new StateModel object and set the stateName and stateId of the stateRequestBean
//call findById method of stateRepository to get the state by stateId
//if findById method returns empty then call switchIfEmpty() and return error message,
//otherwise call flatMap() and updateState method of stateRepository to update the state by stateName and stateId
//if updateState method returns empty then call switchIfEmpty() and return error message,
//otherwise call flatMap() and return "Success"
//if any error occurs then call onErrorResume() and return "error"
    public Mono<String> updateState( StateRequestBean stateRequestBean){
List<SError> errorList=new ArrayList<>();
        StateModel stateModel = new StateModel();
        stateModel.setStateName(stateRequestBean.getStateName());
        stateModel.setStateId(stateRequestBean.getStateId());
        return stateRepository.findById(stateModel.getStateId())
                .flatMap(exitState->{
            return stateRepository.updateState(stateModel.getStateName(),stateModel.getStateId())
                    .switchIfEmpty(Mono.error(new Exception("not updated")))
                    .flatMap(user -> Mono.just("Success"));
                })
                .switchIfEmpty( Mono.just("not found"))
              .onErrorResume(error -> {
            log.error("Error during user updation: ",error);
            errorList.add(new SError("Error during user updation","123"));
            return Mono.error(new ServiceException(errorList));
        });
    }

//method=saveState
//path=src/main/java/com/rutuja/state/service/StateService.java
//input=stateRequestBean
//output=Mono<String>
//create a new StateModel object and set the stateName and countryId of the stateRequestBean
//call selectValue method of sequenceRepository to get the sequence value
//if selectValue method returns empty then call switchIfEmpty() and return error message,
//otherwise call flatMap() and set the stateId of the stateModel
//call findBystateName method of stateRepository to get the state by stateName
//if findBystateName method returns empty then call switchIfEmpty() and return error message,
//otherwise call defer() and insert method of r2dbcEntityTemplate to insert the stateModel
//if insert method returns empty then call switchIfEmpty() and return error message,
//otherwise call flatMap() and updateSequenceState method of sequenceRepository to update the sequence value
//if updateSequenceState method returns empty then call switchIfEmpty() and return error message,
//otherwise call flatMap() and return "Successfully save state"
//if any error occurs then call onErrorResume() and return "error"
@Transactional
public Mono<String> saveState(StateRequestBean stateRequestBean)  {
    List<SError> errorList=new ArrayList<>();
    StateModel stateModel = new StateModel();
    stateModel.setStateName(stateRequestBean.getStateName());
    stateModel.setCountryId(stateRequestBean.getCountry().getCountryId());
    stateModel.setAction("active");
    return sequenceRepository.selectValue()
            .switchIfEmpty(Mono.error(new Exception("Not Found")))
            .flatMap(sequenceValue -> {
                stateModel.setStateId(sequenceValue);
                return stateRepository.findBystateName(stateModel.getStateName())
                        .flatMap(exitstate -> Mono.just("State already exists"))
                        .switchIfEmpty(Mono.defer(() -> {
                            return r2dbcEntityTemplate.insert(stateModel)
                                    .switchIfEmpty(Mono.error(new Exception("state not saved")))
                                    .flatMap(state -> {
                                        return sequenceRepository.updateSequenceState(stateModel.getStateId()+1)
                                                .switchIfEmpty(Mono.error(new Exception("not updated")))
                                                .flatMap(integer -> Mono.just("Successfully save state"))
                                                .onErrorResume(error -> {
                                                    errorList.add(new SError("Error during user updation","104"));
                                                    log.error("Error during user updation: ", error);
                                                    return Mono.error(new ServiceException(errorList));
                                                });
                                    });
                        }));
            })
            .onErrorResume(error -> {
                errorList.add(new SError("Error during insert updation","111"));
                log.error("Error during insert updation: ", error);
                return Mono.error(new ServiceException(errorList));
            });
}

//method=delete
//path=src/main/java/com/rutuja/state/service/StateService.java
//input=stateId
//output=Mono<String>
//call updateByAction method of stateRepository to update the state by stateId
//if updateByAction method returns empty then call switchIfEmpty() and return error message,
    //otherwise call flatMap() and return "Success"
    //if any error occurs then call onErrorResume() and return "error"
    public Mono<String> delete(Integer stateId){
        List<SError> errorList=new ArrayList<>();
       Mono<Integer> updatedByAction= stateRepository.updateByAction(stateId);
        errorList.add(new SError("Error during state deletion","103"));
        return  updatedByAction .flatMap(stateInt -> Mono.just("Success"))
                .switchIfEmpty(Mono.error(new ServiceException(errorList)))
                .onErrorResume(error->{
                    errorList.add(new SError("Error during state updation","103"));
                    log.error("Error during state updation: ",error);
                    return Mono.error(new ServiceException(errorList));
                });
    }


//method=getStateByCountryId
//path=src/main/java/com/rutuja/state/service/StateService.java
//input=countryId
//output=Flux<StateModel>
//calling the findBycountryId method of stateRepository to get the states by countryId
//if findBycountryId method returns empty then call switchIfEmpty() and return error message,
//otherwise call flatMap() and return the stateModel
 //if any error occurs then call onErrorResume() and return "error"
    public Flux<StateModel> getStateByCountryId(Integer countryId){
        return stateRepository.findBycountryId(countryId)
                .flatMap(state->Flux.just(state))
                .switchIfEmpty(Flux.error(new Exception("States not found")))
                .onErrorResume(error -> {
                    log.error("Error during state retrieval: ", error);
                    return Flux.error(new RuntimeException("Unable to fetch state details"));
                });
    }

    public Mono<String> updateStateByPatch(Integer id, StateModel stateModel) {
        return stateRepository.findById(id)
                .flatMap(state -> {
                    boolean updated = false;

                    if (stateModel.getStateName() != null) {
                        state.setStateName(stateModel.getStateName());
                        updated = true;
                    }

                    if (stateModel.getCountryId() != null) {
                        state.setCountryId(stateModel.getCountryId());
                        updated = true;
                    }

                    if (updated) {
                        return stateRepository.save(state).thenReturn("Update Successfully");
                    } else {
                        return Mono.just("No fields to update");
                    }
                })
                .switchIfEmpty(Mono.just("State not found"))
                .onErrorResume(e->{
                    return Mono.error(new Exception("error occour at the time find state"));
                });
    }


}
