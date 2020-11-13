package com.example.ex;

import android.os.Handler;
import android.widget.CheckBox;
import android.widget.RatingBar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ex.cells.AbsResultCell;
import com.example.ex.cells.ButtonCell;
import com.example.ex.cells.Tuple;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private static final int PROGRESS_STEP = 5;
    private static final int DEFAULT_SLEEP_TIME = 200;
    private static final int HUNDRED = 100;

    private final MutableLiveData<State> tupleLiveData = new MutableLiveData<>();
    private final ArrayList<AbsResultCell> tupleArrayList = new ArrayList<>();
    private final State state;
    private final MutableLiveData<String> toastMessageObserver = new MutableLiveData<>();
    private int progressBarStatus;
    private final Handler progressBarHandler = new Handler();


    public MainViewModel() {
        state = new State();
        tupleLiveData.setValue(state);
    }

    public final LiveData<String> getToastObserver(){
        return toastMessageObserver;
    }

    public void update(final FragmentActivity activity, final RecyclerView recyclerView){
        populateList();
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(activity,
                tupleArrayList, new TripListActionListener() {
            @Override
            public void onKeyClick(final String text) {
                state.setText(text);
            }

            @Override
            public void onRatingChanged(final int adapterPosition, final int rating) {

                final Tuple tuple = (Tuple) tupleArrayList.get(adapterPosition);

                switch (tuple.getIndex()){
                    case 0:
                        state.setPeople(rating);
                        break;
                    case 1:
                        state.setAircraft(rating);
                        break;
                    case 2:
                        state.setSeat(rating);
                        break;
                    case 3:
                        state.setCrew(rating);
                        break;
                    case 4:
                        state.setFood(rating);
                        break;
                    default:
                        state.setAircraft(tuple.getIndex());
                }
            }

            @Override
            public void onButtonClick() {
                enableViews(false);
                progressBarStatus = 0;
                runAsync();
            }

            @Override
            public void onCheckBoxClick(final RatingBar ratingBar, final CheckBox checkBox) {
                ratingBar.setRating(0);

                if (checkBox.isChecked()){
                    ratingBar.setEnabled(false);
                    state.setFood(-1);
                } else {
                    ratingBar.setEnabled(true);
                    state.setFood(0);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemViewCacheSize(5);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public MutableLiveData<State> getUserMutableLiveData() {
        return tupleLiveData;
    }

    private void populateList(){
        tupleArrayList.clear();
        addTuple("How crowded was the flight?", state.getPeople(), false, 0);
        addTuple("How do you rate the aircraft?", state.getAircraft(), false, 1);
        addTuple("How do you rate the seats?", state.getSeat(), false, 2);
        addTuple("How do you rate the crew?", state.getCrew(), false, 3);
        addTuple("How do you rate the food?", state.getFood(), true, 4);

        final ButtonCell cell = new ButtonCell(AbsResultCell.ViewType.BUTTON, state.getText(), state.isEnabled());
        tupleArrayList.add(cell);
    }

    private void addTuple(final String title, final int rating, final boolean flag, final int index){

        final AbsResultCell.ViewType viewType = index == 0
                ? AbsResultCell.ViewType.CUSTOM_RATING : AbsResultCell.ViewType.RATING;
        final Tuple tuple = new Tuple(title, rating, state.getFood() == -1, flag,
                index, viewType, state.isEnabled());
        tupleArrayList.add(tuple);
    }


    private void runAsync(){
        new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < HUNDRED) {
                    progressBarStatus += PROGRESS_STEP;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            if (progressBarStatus >= HUNDRED){;
                                show();
                                enableViews(true);
                            }
                        }
                    });
                    try {
                        Thread.sleep(DEFAULT_SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }



    private void enableViews(final boolean enabled){
        state.setEnabled(enabled);
        tupleLiveData.setValue(state);
    }

    private void show(){
        toastMessageObserver.setValue(state.toString());
        toastMessageObserver.setValue(null);
    }
}