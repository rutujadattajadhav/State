package com.rutuja.state.responce;

import com.rutuja.state.error.SError;

import java.util.List;

public class StateResponce2 {
    private Object data;
    private List<SError> error;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<SError> getError() {
        return error;
    }

    public void setError(List<SError> error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "StateResponce{" +
                "data=" + data +
                ", error=" + error +
                '}';
    }


}
