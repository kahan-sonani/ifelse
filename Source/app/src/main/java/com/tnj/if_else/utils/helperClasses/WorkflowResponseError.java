package com.tnj.if_else.utils.helperClasses;

public class WorkflowResponseError<T> extends Response<T> {

    @Override
    public boolean isSuccessful() {
        return false;
    }

    public T getResponse(){
        return response;
    }

    public WorkflowResponseError(T state){
        super();
        response = state;
    }

    public Response<T> setResponse(T response){
        this.response = response;
        return this;
    }
}
