//package com.rutuja.state.service;
//
//
//import com.rutuja.state.model.StateModel;
//import com.rutuja.state.repo.StateRepository;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RunWith(MockitoJUnitRunner.Silent.class)
//public class StateServiceTest {
//
//    @InjectMocks
//    private StateService stateService;
//
//    @Mock
//    private  StateRepository stateRepository;
//
//    @Mock
//    private Optional<StateModel> optional;
//
//    @Test
//    public void getStateByIdSuccess() throws Exception {
//       StateModel stateModell=new StateModel();
//        stateModell.setStateId(1);
//        stateModell.setStateName("Andaman & Nicobar Is");
//       Optional<StateModel>  stateModel1=Optional.of(stateModell);
//        Mockito.when(stateRepository.findById(1)).thenReturn(stateModel1);
//        Mockito.when(optional.get()).thenReturn(stateModell);
//        StateModel stateModelresult =stateService.getStateById(1);
//        Assert.assertEquals(stateModell.toString(),stateModelresult.toString());
//    }
//
//    @Test(expected = Exception.class)
//    public void getStateByIdException() throws Exception {
//        StateModel stateModelresult =stateService.getStateById(1);
//    }
//
//    @Test
//    public void getAllStateSuccess() throws Exception {
//        List<StateModel> stateModelList=new ArrayList<>();
//        StateModel stateModel=new StateModel();
//        stateModel.setStateId(1);
//        stateModel.setStateName("Andaman & Nicobar Is");
//        stateModelList.add(stateModel);
//        Mockito.when(stateRepository.findAll()).thenReturn(stateModelList);
//        List<StateModel> stateModels =stateService.getAllState();
//        Assert.assertEquals(stateModelList.toString(),stateModels.toString());
//    }
//
//    @Test(expected = Exception.class)
//    public void getAllStateException() throws Exception {
//        Mockito.when(stateRepository.findAll()).thenReturn(null);
//        List<StateModel> stateModels =stateService.getAllState();
//    }
//
//    @Test
//    public void getStateSuccess() throws Exception {
//        List<StateModel> stateModelList=new ArrayList<>();
//        StateModel stateModel=new StateModel();
//        stateModel.setStateId(1);
//        stateModel.setStateName("Andaman & Nicobar Is");
//        stateModelList.add(stateModel);
//        Mockito.when(stateRepository.findAllById(Mockito.anyIterable())).thenReturn(stateModelList);
//        List<Integer> integerList=new ArrayList<>();
//        integerList.add(1);
//        integerList.add(2);
//        List<StateModel> stateModels =stateService.getStates(integerList);
//        Assert.assertEquals(stateModelList.toString(),stateModels.toString());
//    }
//
//    @Test(expected = Exception.class)
//    public void getStateException() throws Exception {
//        Mockito.when(stateRepository.findAllById(Mockito.anyIterable())).thenReturn(null);
//        List<Integer> integerList=new ArrayList<>();
//        integerList.add(1);
//        integerList.add(2);
//        List<StateModel> stateModels =stateService.getStates(integerList);
//    }
//}
