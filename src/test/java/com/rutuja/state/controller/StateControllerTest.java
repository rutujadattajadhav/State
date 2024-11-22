package com.rutuja.state.controller;

import com.rutuja.state.model.Country;
import com.rutuja.state.model.StateModel;
import com.rutuja.state.model.StateRequestBean;
import com.rutuja.state.model.StateResponce;
import com.rutuja.state.service.StateService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class StateControllerTest {

    @InjectMocks
    private StateController stateController;

    @Mock
    private StateService stateService;

    @Test
    public void getStateById() throws Exception {
        Mockito.when(stateService.getStateById(Mockito.anyInt())).thenReturn(Mono.just(new StateModel()));
        StateModel modelMono = stateController.getStateById(1).block();
        Assert.assertEquals(new StateModel().toString(),modelMono.toString());
    }

    @Test
    public void getAllState() throws Exception {
        Mockito.when(stateService.getAllState()).thenReturn(Flux.just(new StateResponce()));
        Flux<StateResponce> stateModelFlux =stateController.getAllState();
        Assert.assertEquals(Flux.just(new StateResponce()).toString(),stateModelFlux.toString());
    }

    @Test
    public void getAllStatesByIds() throws Exception {
        Mockito.when(stateService.getStates(Mockito.anyList())).thenReturn(Flux.just(new StateModel()));
        List<Integer> integerList=new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        Flux<StateModel> stateModelFlux=stateController.getStates(integerList);
        Assert.assertEquals(Flux.just(new StateModel()).toString(),stateModelFlux.toString());
    }

@Test
    public void stateUpdate(){
     Mockito.when(stateService.updateState(Mockito.any())).thenReturn(Mono.just("success"));
    StateRequestBean stateRequestBean   =new StateRequestBean();
    stateRequestBean.setStateId(1);
    stateRequestBean.setStateName("Maharashtra");
    Country country = new Country();
    country.setCountryId(1);
    stateRequestBean.setCountry(country);
    stateRequestBean.getCountry().getCountryId();
    stateRequestBean.setAction("active");
    Mono<String> updateState=stateController.updateState(stateRequestBean);
    Assert.assertEquals("success",updateState.block());
    }

    @Test
    public void stateSave(){
        Mockito.when(stateService.saveState(Mockito.any())).thenReturn(Mono.just("success"));
        StateRequestBean stateRequestBean   =new StateRequestBean();
        stateRequestBean.setStateId(1);
        stateRequestBean.setStateName("Maharashtra");
        Country country = new Country();
        country.setCountryId(1);
        stateRequestBean.setCountry(country);
        stateRequestBean.getCountry().getCountryId();
        stateRequestBean.setAction("active");
        Mono<String> saveState=stateController.saveState(stateRequestBean);
        Assert.assertEquals("success",saveState.block());
    }

    @Test
    public void deleteState(){
        Mockito.when(stateService.delete(Mockito.anyInt())).thenReturn(Mono.just("success"));
        Mono<String> deleteState=stateController.delete(1);
        Assert.assertEquals("success",deleteState.block());
    }

    @Test
    public void getStateByCountryId(){
        Mockito.when(stateService.getStateByCountryId(Mockito.anyInt())).thenReturn(Flux.just(new StateModel()));
        Flux<StateModel> stateModelFlux=stateController.getStateByCountryId(1);
        Assert.assertEquals(Flux.just(new StateModel()).toString(),stateModelFlux.toString());
    }
}









