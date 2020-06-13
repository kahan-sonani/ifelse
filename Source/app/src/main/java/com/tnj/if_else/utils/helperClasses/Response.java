package com.tnj.if_else.utils.helperClasses;

public abstract class Response<T>{

    protected boolean isFinished;
    protected T response;

    public abstract boolean isSuccessful();

    public boolean isFinished() {
        return isFinished;
    }

    public Response<T> finished(boolean value){
        isFinished = value;
        return this;
    }

    public Response(){
        isFinished = false;
    }

    public T getResponse() {
        return response;
    }
}
