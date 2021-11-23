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
import com.example.ex.cells.RatingCell;
import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private static final int PROGRESS_STEP = 5;
    private static final int DEFAULT_SLEEP_TIME = 200;
    private static final int HUNDRED = 100;

    private final MutableLiveData<State> cellLiveData = new MutableLiveData<>();
    private final ArrayList<AbsResultCell> cellArrayList = new ArrayList<>();
    private final State state;
    private final MutableLiveData<String> toastMessageObserver = new MutableLiveData<>();
    private int progressBarStatus;
    private final Handler progressBarHandler = new Handler();


    public MainViewModel() {
        state = new State();
        cellLiveData.setValue(state);
    }

    public final LiveData<String> getToastObserver(){
        return toastMessageObserver;
    }

    /**
     * Method to update a cell array after the state had changed
     * @param activity current activity
     * @param recyclerView recycler view of the activity
     */
    public void update(final FragmentActivity activity, final RecyclerView recyclerView){
        populateList();
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(activity,
                cellArrayList, new TripListActionListener() {
            /**
             * Method realization for RecyclerViewHolderButton for EditText on key click event
             * It saves text from EditText to the state object
             * @param text EditText's text
             */
            @Override
            public void onKeyClick(final String text) {
                state.setText(text);
            }

            /**
             * Method realization for RecyclerViewHolderRating for Rating bar on rating change event
             * It saves new rating to the state object
             * @param adapterPosition position of current cell in the list
             * @param rating new rating
             */
            @Override
            public void onRatingChanged(final int adapterPosition, final int rating) {

                final RatingCell ratingCell = (RatingCell) cellArrayList.get(adapterPosition);

                switch (ratingCell.getIndex()){
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
                        state.setAircraft(ratingCell.getIndex());
                }
            }

            /**
             * Method realization for RecyclerViewHolderButton for button on click event
             * It starts a collecting data emulation and enables all input fields
             */
            @Override
            public void onButtonClick() {
                enableViews(false);
                progressBarStatus = 0;
                runAsync();
            }

            /**
             * Method realization for RecyclerViewHolderRating for check box on click event
             * It sets food rating bar's rating to 0 and saves new instance to the state object
             * @param ratingBar food rating bar to change
             * @param checkBox checkbox that was invoked
             */
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

        // setting a recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemViewCacheSize(5);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public MutableLiveData<State> getUserMutableLiveData() {
        return cellLiveData;
    }

    /**
     * This method recreates a cell list for recycler view
     */
    private void populateList(){
        cellArrayList.clear();
        addCell("How crowded was the flight?", state.getPeople(), false, 0);
        addCell("How do you rate the aircraft?", state.getAircraft(), false, 1);
        addCell("How do you rate the seats?", state.getSeat(), false, 2);
        addCell("How do you rate the crew?", state.getCrew(), false, 3);
        addCell("How do you rate the food?", state.getFood(), true, 4);

        final ButtonCell cell = new ButtonCell(AbsResultCell.ViewType.BUTTON, state.getText(), state.isEnabled());
        cellArrayList.add(cell);
    }

    /**
     * This method creates a new cell and adds it to the cell list
     * @param title a title of rating bar
     * @param rating value of rating bar
     * @param flag tells if this cell needs a checkbox
     * @param index an index of cell
     */
    private void addCell(final String title, final int rating, final boolean flag, final int index){

        final AbsResultCell.ViewType viewType = index == 0
                ? AbsResultCell.ViewType.CUSTOM_RATING : AbsResultCell.ViewType.RATING;
        final RatingCell ratingCell = new RatingCell(title, rating, state.getFood() == -1, flag,
                index, viewType, state.isEnabled());
        cellArrayList.add(ratingCell);
    }


    /**
     * This method emulates collecting data, calls a show method and enable elements of view
     */
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


    /**
     * Enables or disables elements
     * @param enabled tells if the method should disable or enable views
     */
    private void enableViews(final boolean enabled){
        state.setEnabled(enabled);
        cellLiveData.setValue(state);
    }

    /**
     * Changes the livedata with string message to callback to fragment and show the state info
     */
    private void show(){
        toastMessageObserver.setValue(state.toString());
        toastMessageObserver.setValue(null);
    }
}