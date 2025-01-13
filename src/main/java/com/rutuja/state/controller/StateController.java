package com.rutuja.state.controller;

import com.rutuja.state.error.SError;
import com.rutuja.state.exception.ServiceException;
import com.rutuja.state.exception.ValidationException;
import com.rutuja.state.model.StateModel;
import com.rutuja.state.model.StateRequestBean;
import com.rutuja.state.model.StateResponce;
import com.rutuja.state.responce.StateResponce2;
import com.rutuja.state.service.StateService;
import com.rutuja.state.validation.DeleteStateValidation;
import com.rutuja.state.validation.SaveStateValidation;
import com.rutuja.state.validation.UpdateStateValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
public class StateController {
    @Autowired
    private StateService stateService;

    Logger log= LoggerFactory.getLogger(this.getClass());

    //method=getStateById
    //path=stateById/{stateId}
    //input=stateId
    //output=StateModel
    //description=This method is used to get state by stateId
    //return=StateModel
    //calling=stateService.getStateById(stateId) : return this method Mono<StateModel>
 @GetMapping(value = "stateById/{stateId}")
public Mono<StateResponce2> getStateById(@PathVariable("stateId") Integer stateId) throws ServiceException {
     return stateService.getStateById(stateId)
             .map(state -> {
                 StateResponce2 stateResponse = new StateResponce2();
                 stateResponse.setData(state);
                 return stateResponse; })
     .onErrorResume(e -> { if (e instanceof ServiceException) {
         StateResponce2 stateResponse = new StateResponce2();
        List  eer=((ServiceException) e).getErrorMessage();
         stateResponse.setError(eer);
          return Mono.just(stateResponse);
     }
         return Mono.error(e);
     });
}

//method=getAllState
    //path=getAllState
    //input=No input
    //output=List<StateResponce>
    //description=This method is used to get all state
    //return=List<StateResponce>
    //calling=stateService.getAllState() : return this method Flux<StateResponce>
 @GetMapping("/getAllState")
 public Flux<StateResponce> getAllState() throws Exception {
     return stateService.getAllState() ;
 }

 //method=getStates
    //path=getStates
    //input=List<Integer> stateIds
    //output=List<StateModel>
    //description=This method is used to get states by stateIds
    //return=List<StateModel>
    //calling=stateService.getStates(stateIds) : return this method Flux<StateModel>
 @GetMapping("/getStates")
 public Flux<StateModel> getStates(@RequestBody List<Integer> stateIds) throws Exception {
     return stateService.getStates(stateIds);
 }

    //method=updateState
        //path=updateState
        //input=StateRequestBean
        //output=String
        //description=This method is used to update state
        //return=String
        //calling=stateService.updateState(stateRequestBean) : return this method Mono<String>
    @Autowired
    private UpdateStateValidation updateStateValidation;
    @PutMapping("/updateState")
    public Mono<StateResponce2> updateState(@RequestBody StateRequestBean stateRequestBean){
    return   Mono.fromCallable(()->{
         updateStateValidation.validate(stateRequestBean);
         return stateRequestBean;
     })
            .flatMap(validatedRequest ->
                    stateService.updateState(validatedRequest)
                            .flatMap(updatedState -> {
                                StateResponce2 stateResponce2 = new StateResponce2();
                                stateResponce2.setData(updatedState);
                                return Mono.just( stateResponce2);
                            })
                            .onErrorResume(e->{
                                if (e instanceof ServiceException) {
                                    StateResponce2 stateResponce2 = new StateResponce2();
                                    stateResponce2.setError(((ServiceException) e).getErrorMessage());
                                    return Mono.just(stateResponce2);
                                }
                                return Mono.error(e);
                            })
            )
            .onErrorResume(e -> {
                if (e instanceof ValidationException) {
                    StateResponce2 stateResponse = new StateResponce2();
                    List  eer=((ValidationException) e).getErrorList();
                    stateResponse.setError(eer);
                    return Mono.just(stateResponse);
                } else

                    return Mono.error(e);
            });

}

    //method=saveState
        //path=saveState
        //input=StateRequestBean
        //output=String
        //description=This method is used to save state
        //return=String
        //calling=stateService.saveState(stateRequestBean) : return this method Mono<String>

    @Autowired
    private SaveStateValidation saveStateValidation;
    @PostMapping("/saveState")
    public Mono<StateResponce2> saveState(@RequestBody StateRequestBean stateRequestBean) throws Exception {
        return Mono.fromCallable(() -> {
            saveStateValidation.validate(stateRequestBean);
            return stateRequestBean;
        })
                .flatMap(validatedRequest ->
                        stateService.saveState(validatedRequest)
                                .flatMap(savedState -> {
                                    StateResponce2 stateResponce2 = new StateResponce2();
                                    stateResponce2.setData(savedState);
                                    return Mono.just( stateResponce2);
                                })
                                .onErrorResume(e->{
                                    if (e instanceof ServiceException) {
                                        StateResponce2 stateResponce2 = new StateResponce2();
                                        stateResponce2.setError(((ServiceException) e).getErrorMessage());
                                        return Mono.just(stateResponce2);
                                    }
                                    return Mono.error(e);
                                })
                )
                .onErrorResume(e -> {
                    if (e instanceof ValidationException) {
                        StateResponce2 stateResponse = new StateResponce2();
                        List  eer=((ValidationException) e).getErrorList();
                        stateResponse.setError(eer);
                        return Mono.just(stateResponse);
                    } else

                        return Mono.error(e);
                });

    }

    //method=delete
        //path=deleteState/{deleteById}
        //input=stateId
        //output=String
        //description=This method is used to delete state
        //return=String
        //calling=stateService.delete(stateId) : return this method Mono<String>
    @Autowired
    private DeleteStateValidation deleteStateValidation;
    @DeleteMapping(value="deleteState/{deleteById}")
    public Mono<StateResponce2> delete(@PathVariable("deleteById") Integer stateId) {
        return Mono.fromCallable(() -> {
            deleteStateValidation.validate(stateId);
            return stateId;
        })
                .flatMap(validatedStateId ->
                        stateService.delete(validatedStateId)
                                .flatMap(deteltd->{
                                    StateResponce2 stateResponse = new StateResponce2();
                                    stateResponse.setData(deteltd);
                                    return Mono.just(stateResponse);
                                })

                                .onErrorResume(e -> {
                                    if (e instanceof ServiceException) {
                                        StateResponce2 stateResponse = new StateResponce2();
                                        stateResponse.setError(((ServiceException) e).getErrorMessage());
                                        return Mono.just(stateResponse);
                                    } else {
                                        return Mono.error(e); } }) )
                .onErrorResume(e -> {
                    if (e instanceof ValidationException) {
                        StateResponce2 stateResponse = new StateResponce2();
                        List eer = ((ValidationException) e).getErrorList();
                        stateResponse.setError(eer);
                        return Mono.just(stateResponse );
                    } else {
                        return Mono.error(e);
                    }
                });
    }

    //method=getStateByCountryId
        //path=countryId/{countryId}
        //input=countryId
        //output=List<StateModel>
        //description=This method is used to get state by countryId
        //return=List<StateModel>
        //calling=stateService.getStateByCountryId(countryId) : return this method Flux<StateModel>
    @GetMapping(value="countryId/{countryId}")
    public Flux<StateModel> getStateByCountryId(@PathVariable("countryId") Integer countryId) {
        return stateService.getStateByCountryId(countryId);
    }

    @PatchMapping("updatebyPatch/{id}")
    public Mono<String> updateUserbyPatch(@PathVariable Integer id,@RequestBody StateModel stateModel){
      return stateService.updateStateByPatch(id,stateModel);
    }
}