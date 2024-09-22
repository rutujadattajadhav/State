package com.rutuja.state.controller;

import com.rutuja.state.model.StateModel;
import com.rutuja.state.service.StateService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class StateControllerTest {

    @InjectMocks
    private StateController stateController;

    @Mock
    private StateService stateService;

    @Test
    public void getStateByIdSuccess() throws Exception {
        Mockito.when(stateService.getStateById(Mockito.anyInt())).thenReturn(new StateModel());
        StateModel stateModel =stateController.getStateById(1);
        Assert.assertEquals(new StateModel().toString(),stateModel.toString());
    }

    @Test
    public void getAllState() throws Exception {
        List<StateModel> stateModels=new ArrayList<>();
        Mockito.when(stateService.getAllState()).thenReturn(stateModels);
        List<StateModel> allState=stateController.getAllState();
        Assert.assertEquals(stateModels.toString(),allState.toString());
    }
    @Test
    public void getStateSuccess() throws Exception {
        List<StateModel> stateModelList=new ArrayList<>();
        Mockito.when(stateService.getStates(Mockito.anyList())).thenReturn(stateModelList);
        List<Integer> integerList=new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        List<StateModel> stateModels=stateController.getStates(integerList);
        Assert.assertEquals(stateModelList.toString(),stateModels.toString());
    }
}
