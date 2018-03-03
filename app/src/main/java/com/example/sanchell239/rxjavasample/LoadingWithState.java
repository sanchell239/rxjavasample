package com.example.sanchell239.rxjavasample;



public class LoadingWithState {
    private String result = null;
    private State LoadingState = State.None;

    public LoadingWithState() {
        LoadingState = State.Loading;
    }

    public LoadingWithState(State myState, String info) {
        LoadingState = myState;
        result = "результат: " + info;
    }

    public boolean isLoading() {
        return LoadingState == State.Loading;
    }

    public boolean isError() {
        return LoadingState == State.Error;
    }

    public boolean isSuccess() {
        return LoadingState == State.Success;
    }

    public String getResult() {
        return result;
    }
}