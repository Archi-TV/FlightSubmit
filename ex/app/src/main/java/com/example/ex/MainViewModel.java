package com.example.ex;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<State> tupleLiveData = new MutableLiveData<>();

    public MainViewModel() {
        tupleLiveData.setValue(new State());
    }

    public MutableLiveData<State> getUserMutableLiveData() {
        return tupleLiveData;
    }

}