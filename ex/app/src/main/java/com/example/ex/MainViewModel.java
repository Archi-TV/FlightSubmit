package com.example.ex;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<State> tupleLiveData = new MutableLiveData<>();

    public MainViewModel() {
        select(new State());
    }

    public void select(State state) {
        tupleLiveData.setValue(state);
    }

    public MutableLiveData<State> getUserMutableLiveData() {
        return tupleLiveData;
    }

}