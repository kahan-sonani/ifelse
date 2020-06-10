package com.tnj.if_else.utils.helperClasses;

import com.tnj.if_else.utils.interfaces.State;

public class Response<T extends State>{

    protected boolean isFinished;
    protected T response;

    public boolean isFinished() {
        return isFinished;
    }

    public Response<T> finished(boolean value){
        isFinished = value;
        return this;
    }

    public Response(){
        isFinished = false;
        response = (T) new NoState();
    }

    public Response(T value){
        response = value;
    }

    public T getResponse() {
        return response;
    }

    public Response<T> setResponse(T response) {
        this.response = response;
        return this;
    }
}
