package com.rutuja.state.controller;

import com.rutuja.state.model.StateModel;
import com.rutuja.state.service.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class StateController {
    @Autowired
    private StateService stateService;

    Logger log= LoggerFactory.getLogger(this.getClass());

 @GetMapping(value = "/{stateId}")
public StateModel getStateById(@PathVariable("stateId") Integer stateId) throws Exception {
     log.debug("stateId input :"+stateId);
    return stateService.getStateById(stateId);
}

 @GetMapping("/getAllState")
 public List<StateModel> getAllState() throws Exception {
     return stateService.getAllState() ;
 }
 @GetMapping("/getStates")
 public List<StateModel> getStates(@RequestBody List<Integer> stateIds) throws Exception {
     return stateService.getStates(stateIds);
 }
}
