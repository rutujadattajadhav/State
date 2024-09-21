package com.rutuja.state.service;

import com.rutuja.state.model.StateModel;
import com.rutuja.state.repo.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateService {

    Logger log= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StateRepository stateRepository;

    public StateModel getStateById(Integer stateId) throws Exception {
       if(stateRepository.findById(stateId).isPresent()){
           log.debug("Id is present in the table");
        return   stateRepository.findById(stateId).get();
       }
       throw new Exception("State not found");
    }

    public List<StateModel> getAllState() throws Exception {
        List<StateModel> listOfState=new ArrayList<>();
        Iterable<StateModel> states=stateRepository.findAll();
        if(stateRepository!=null){
            states.forEach((state)->{
                listOfState.add(state);
            });
            return listOfState;
        }
        throw new Exception("State not found");
    }

    public List<StateModel> getStates(List<Integer> stateIds) throws Exception {
        List<StateModel> listOfState=new ArrayList<>();
        Iterable<StateModel> states=stateRepository.findAllById(stateIds);
        if(states!=null){
            states.forEach((state)->{
                listOfState.add(state);
            });
            return listOfState;
        }
        throw new Exception("States not found");
    }
}