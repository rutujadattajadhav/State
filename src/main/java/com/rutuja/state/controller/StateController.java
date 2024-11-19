package com.rutuja.state.controller;

import com.rutuja.state.model.StateModel;
import com.rutuja.state.model.StateRequestBean;
import com.rutuja.state.model.StateResponce;
import com.rutuja.state.service.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
public Mono<StateModel> getStateById(@PathVariable("stateId") Integer stateId) throws Exception {
     log.debug("stateId input :"+stateId);
    return stateService.getStateById(stateId);
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
@PutMapping("/updateState")
    public Mono<String> updateState(@RequestBody StateRequestBean stateRequestBean){
     return stateService.updateState(stateRequestBean);
    }

    //method=saveState
        //path=saveState
        //input=StateRequestBean
        //output=String
        //description=This method is used to save state
        //return=String
        //calling=stateService.saveState(stateRequestBean) : return this method Mono<String>
    @PostMapping("/saveState")
    public Mono<String> saveState(@RequestBody StateRequestBean stateRequestBean){
        return stateService.saveState(stateRequestBean);
    }

    //method=delete
        //path=deleteState/{deleteById}
        //input=stateId
        //output=String
        //description=This method is used to delete state
        //return=String
        //calling=stateService.delete(stateId) : return this method Mono<String>
    @DeleteMapping(value="deleteState/{deleteById}")
    public Mono<String> delete(@PathVariable("deleteById") Integer stateId) {
        return stateService.delete(stateId);
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
}