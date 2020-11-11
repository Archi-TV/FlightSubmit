package com.example.ex;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Tuple>> tupleLiveData = new MutableLiveData<>();
    private final ArrayList<Tuple> tupleArrayList = new ArrayList<>();
    private final State state = new State();

    public MainViewModel() {
        init();
    }

    State getState() {
        return state;
    }

    MutableLiveData<ArrayList<Tuple>> getUserMutableLiveData() {
        return tupleLiveData;
    }

    private void init(){
        populateList();
        tupleLiveData.setValue(tupleArrayList);
    }

    private void populateList(){

        addTuple("How crowded was the flight?", state.getPeople(), false);
        addTuple("How do you rate the aircraft?", state.getAircraft(), false);
        addTuple("How do you rate the seats?", state.getSeat(), false);
        addTuple("How do you rate the crew?", state.getCrew(), false);
        addTuple("How do you rate the aircraft?", state.getFood(), true);
    }

    private void addTuple(final String title, final int rating, final boolean flag){
        final Tuple tuple = new Tuple();
        tuple.setTitle(title);
        tuple.setRating(rating);

        if (flag){
            tuple.setChecked(state.getFood() == -1);
            tuple.setCheckBoxText("  There were no food");
        }

        tupleArrayList.add(tuple);
    }
}