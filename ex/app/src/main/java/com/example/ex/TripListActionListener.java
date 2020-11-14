package com.example.ex;

import android.widget.CheckBox;
import android.widget.RatingBar;

/**
 * Interface for view model to make realization of event handlers of recyclerview holders
 */
public interface TripListActionListener {
    void onKeyClick(String text);
    void onRatingChanged(final int adapterPosition, final int rating);
    void onCheckBoxClick(final RatingBar ratingBar, final CheckBox checkBox);
    void onButtonClick();
}
