package com.tnj.if_else.utils.helperClasses;

import com.tnj.if_else.utils.interfaces.SuccessState;

public class WorkflowResponseSuccess<T> extends Response<T> {

    private SuccessState state;

    @Override
    public boolean isSuccessful() {
        return true;
    }

    public WorkflowResponseSuccess(){
        super();
        this.response = null;
    }
    public WorkflowResponseSuccess(T response){
        this.response = response;
    }

    public T getResponse(){
        return response;
    }

    public Response<T> setState(SuccessState state) {
        this.state = state;
        return this;
    }

    public SuccessState getState() {
        return state;
    }

    public WorkflowResponseSuccess<T> setResponse(T response){
        this.response = response;
        return this;
    }
}
