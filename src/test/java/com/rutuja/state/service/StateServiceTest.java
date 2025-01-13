  package com.rutuja.state.service;


import com.rutuja.state.exception.ServiceException;
import com.rutuja.state.model.Country;
import com.rutuja.state.model.StateModel;
import com.rutuja.state.model.StateRequestBean;
import com.rutuja.state.model.StateResponce;
import com.rutuja.state.repo.SequenceRepository;
import com.rutuja.state.repo.StateRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class StateServiceTest {

    @InjectMocks
    private StateService stateService;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private Optional<StateModel> optional;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Mock
    private SequenceRepository sequenceRepository;
    @Test
    public void getStateByIdSuccess() throws Exception {

        Mockito.when(stateRepository.findBystateId(Mockito.anyInt())).thenReturn(Mono.just(new StateModel()));
        Mono<StateModel> modelMono=stateService.getStateById(1);
        Assert.assertEquals(new StateModel().toString(),modelMono.block().toString());
    }

    @Test(expected = Exception.class)
    public void getAllStateError() {
        StateModel stateModel1 = new StateModel();
        stateModel1.setStateName("Maharashtra");
        stateModel1.setStateId(1);
        stateModel1.setAction("Active");
        stateModel1.setCountryId(1);

        StateResponce stateResponce = new StateResponce();
        stateResponce.setStateName("Maharashtra");
        stateResponce.setStateId(1);
        stateResponce.setAction("Active");
        Country country = new Country();
        country.setCountryId(1);
        country.setCountryName("India");
        stateResponce.setCountry(country);
        Mockito.when(webClientBuilder.build()).thenReturn(webClient );
        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.headers(Mockito.any())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToMono(Country.class)).thenReturn(Mono.just(country));
        Mockito.when(stateRepository.findAllByAction()).thenReturn(Flux.error(new Exception()));
         stateService.getAllState().blockLast();

    }


    @Test
    public void getAllStateSuccess() throws Exception {
        StateModel stateModel1 = new StateModel();
        stateModel1.setStateName("Maharashtra");
        stateModel1.setStateId(1);
        stateModel1.setAction("Active");
        stateModel1.setCountryId(1);

        StateResponce stateResponce = new StateResponce();
        stateResponce.setStateName(null);
        stateResponce.setStateId(null);
        stateResponce.setAction(null);
        Country country = new Country();
        country.setCountryId(null);
        country.setCountryName(null);
        stateResponce.setCountry(country);

        Mockito.when(stateRepository.findAllByAction()).thenReturn(Flux.just(new StateModel()));
        Mockito.when(webClientBuilder.build()).thenReturn(webClient);
        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.headers(Mockito.any())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToMono(Country.class)).thenReturn(Mono.just(country));
        Flux<StateResponce> allState = stateService.getAllState();
        Assert.assertEquals(Flux.just(stateResponce).blockFirst() , allState.blockFirst());
    }

    @Test(expected = Exception.class)
    public void getAllState_CountryErrror() throws Exception {
        StateModel stateModel1 = new StateModel();
        stateModel1.setStateName("Maharashtra");
        stateModel1.setStateId(1);
        stateModel1.setAction("Active");
        stateModel1.setCountryId(1);

        StateResponce stateResponce = new StateResponce();
        stateResponce.setStateName(null);
        stateResponce.setStateId(null);
        stateResponce.setAction(null);
        Country country = new Country();
        country.setCountryId(null);
        country.setCountryName(null);
        stateResponce.setCountry(country);

        Mockito.when(stateRepository.findAllByAction()).thenReturn(Flux.just(new StateModel()));
        Mockito.when(webClientBuilder.build()).thenReturn(webClient);
        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.headers(Mockito.any())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToMono(Country.class)).thenReturn(Mono.error(new Exception()));
         stateService.getAllState().blockLast();
        //Assert.assertEquals(Flux.empty() , allState);
    }


    @Test(expected = Exception.class)
    public void getAllStateByIdsSuccess(){

        List<Integer> integerList=new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        Mockito.when(stateRepository.findAllByAction(Mockito.anyList())).thenReturn(Flux.error(new Exception()));
      stateService.getStates(integerList).blockLast();
    }

    @Test
    public void updateStateSuccess() throws Exception {
        StateModel stateModel = new StateModel();
        stateModel.setStateName("Maharashtra");
        stateModel.setStateId(1);

        StateRequestBean stateRequestBean = new StateRequestBean();
        stateRequestBean.setStateName("Maharashtra");
        stateRequestBean.setStateId(1);

        Mockito.when(stateRepository.findById(stateModel.getStateId())).thenReturn(Mono.just(stateModel));
        Mockito.when(stateRepository.updateState(stateModel.getStateName(), stateModel.getStateId())).thenReturn(Mono.just(1));

        Mono<String> updateState = stateService.updateState(stateRequestBean);
        Assert.assertEquals("Success", updateState.block());
    }

    @Test
    public void updateStateError()  {
        StateModel stateModel = new StateModel();
        stateModel.setStateName("Maharashtra");
        stateModel.setStateId(1);
        Mockito.when(stateRepository.findById(Mockito.anyInt())).thenReturn(Mono.error(new Exception("error")));
        StateRequestBean stateRequestBean = new StateRequestBean();
        stateRequestBean.setStateName("Maharashtra");
        stateRequestBean.setStateId(1);
        Mockito.when(stateRepository.updateState(stateModel.getStateName(), stateModel.getStateId())).thenReturn(Mono.error(new Exception()));
        Mono<String> updateState = stateService.updateState(stateRequestBean);
        Assert.assertEquals("error", updateState.block());
    }

    @Test
    public void saveStateSuccess() throws ServiceException {
        StateModel stateModel = new StateModel();
        stateModel.setStateName("TestState");
        stateModel.setCountryId(1);
        stateModel.setAction("active");
        stateModel.setStateId(1);

        StateRequestBean stateRequestBean = new StateRequestBean();
        stateRequestBean.setStateName("TestState");
        Country country = new Country();
        country.setCountryId(1);
        stateRequestBean.setCountry(country);

        Mockito.when(sequenceRepository.selectValue()).thenReturn(Mono.just(1));
        Mockito.when(stateRepository.findBystateName("TestState")).thenReturn(Mono.empty());
        Mockito.when(r2dbcEntityTemplate.insert(stateModel)).thenReturn(Mono.just(stateModel));
        Mockito.when(sequenceRepository.updateSequenceState(2)).thenReturn(Mono.just(1));
        Mono<String> saveState = stateService.saveState(stateRequestBean);
        String result = saveState.block();

        Assert.assertEquals("Successfully save state", result);
    }



    @Test
    public void testSaveState_InsertError() throws ServiceException {
        StateModel stateModel = new StateModel();
        stateModel.setStateName("TestState");
        stateModel.setCountryId(1);
        stateModel.setAction("active");
        stateModel.setStateId(1);

        StateRequestBean stateRequestBean = new StateRequestBean();
        stateRequestBean.setStateName("TestState");
        Country country = new Country();
        country.setCountryId(1);
        stateRequestBean.setCountry(country);

        Mockito.when(sequenceRepository.selectValue()).thenReturn(Mono.just(1));
        Mockito.when(stateRepository.findBystateName("TestState")).thenReturn(Mono.empty());
        Mockito.when(r2dbcEntityTemplate.insert(stateModel)).thenReturn(Mono.error(new Exception("Error in saving state")));
        Mono<String> saveState = stateService.saveState(stateRequestBean);
        String result=saveState.block();
        Assert.assertEquals("Error during insert updation: Error in saving state", result);
    }

    @Test
    public void findTest_error() throws ServiceException {
        StateRequestBean stateRequestBean = new StateRequestBean();
        stateRequestBean.setStateName("TestState");
        Country country = new Country();
        country.setCountryId(1);
        stateRequestBean.setCountry(country);

        Mockito.when(sequenceRepository.selectValue()).thenReturn(Mono.just(1));
        Mockito.when(stateRepository.findBystateName("TestState")).thenReturn(Mono.error(new Exception("Error in finding state")));

        Mono<String> saveState = stateService.saveState(stateRequestBean);
        String result = saveState.onErrorReturn("Error in finding state").block();
        Assert.assertEquals("Error during insert updation: Error in finding state", result);
    }

    @Test
    public void saveState_ErrorUpdateSuccess() throws ServiceException {
        StateModel stateModel = new StateModel();
        stateModel.setStateName("TestState");
        stateModel.setCountryId(1);
        stateModel.setAction("active");
        stateModel.setStateId(1);

        StateRequestBean stateRequestBean = new StateRequestBean();
        stateRequestBean.setStateName("TestState");
        Country country = new Country();
        country.setCountryId(1);
        stateRequestBean.setCountry(country);

        Mockito.when(sequenceRepository.selectValue()).thenReturn(Mono.just(1));
        Mockito.when(stateRepository.findBystateName("TestState")).thenReturn(Mono.empty());
        Mockito.when(r2dbcEntityTemplate.insert(stateModel)).thenReturn(Mono.just(stateModel));
        Mockito.when(sequenceRepository.updateSequenceState(2)).thenReturn(Mono.error(new Exception("Please try after some time or contact system admin")));
        Mono<String> saveState = stateService.saveState(stateRequestBean);
        String result = saveState.block();
        Assert.assertEquals("Please try after some time or contact system admin", result);
    }

    @Test
    public void deleteSuccess(){
        Mockito.when(stateRepository.updateByAction(1)).thenReturn(Mono.just(1));
        Mono<String> stringMono=stateService.delete(1);
        String result=stringMono.block();
        Assert.assertEquals("Success",result);
    }

    @Test
    public void deleteError(){
        Mockito.when(stateRepository.updateByAction(1)).thenReturn(Mono.error(new Exception()));
        Mono<String> stringMono=stateService.delete(1);
        String result=stringMono.block();
        Assert.assertEquals("error",result);
    }

    @Test
    public void getStateByCountryId(){
        Mockito.when(stateRepository.findBycountryId(Mockito.anyInt())).thenReturn(Flux.just(new StateModel()));
       Flux<StateModel> stateModelFlux= stateService.getStateByCountryId(1);
         Assert.assertEquals(Flux.just(new StateModel()).blockLast(),stateModelFlux.blockLast());
    }

    @Test(expected = Exception.class)
    public void getStateByCountryIdError(){
        Mockito.when(stateRepository.findBycountryId(Mockito.anyInt())).thenReturn(Flux.error(new Exception("Unable to fetch state details")));
        stateService.getStateByCountryId(1).blockLast();
    }

}



