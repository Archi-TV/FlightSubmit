package com.example.ex.holder;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex.MainViewModel;
import com.example.ex.R;
import com.example.ex.RecyclerViewAdapter;
import com.example.ex.cells.AbsResultCell;
import com.example.ex.cells.Tuple;

public final class RecyclerViewViewHolder extends AbsResultHolder {
    private final RatingBar ratingBar;
    private final TextView txtView_title;
    private final CheckBox checkBox;
    private final RecyclerViewAdapter.TripListActionListener tripListActionListener;

    public RecyclerViewViewHolder(@NonNull final View itemView, MainViewModel viewModel,
                                  RecyclerViewAdapter.TripListActionListener tripListActionListener) {
        super(itemView);
        this.tripListActionListener = tripListActionListener;
        ratingBar = itemView.findViewById(R.id.ratingBar);
        txtView_title = itemView.findViewById(R.id.txtView_title);
        checkBox = itemView.findViewById(R.id.checkBox);
    }


    public void bind(@NonNull final AbsResultCell tuple) {

        Tuple cell = (Tuple)tuple;
        txtView_title.setText(cell.getTitle());
        setRating(cell);
        setCheckBox(cell);
    }

    private void setRating(final Tuple tuple){
        ratingBar.setRating(tuple.getRating());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar,
                                        final float rating, final boolean fromUser) {

                final int adapterPosition = getAdapterPosition();
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return;
                }
                tripListActionListener.onRatingChanged(adapterPosition, (int)rating);

                Log.i("rating", Integer.toString((int)rating));
            }
        });
    }

    private void setCheckBox(final Tuple tuple){

        if(!tuple.checkIsNeeded()){
            checkBox.setVisibility(View.GONE);
        } else {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setText(tuple.getCheckBoxText());

            if (tuple.isChecked()) {
                checkBox.performClick();
                ratingBar.setEnabled(false);
            }

            checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {

                    tripListActionListener.onCheckBoxClick(ratingBar, checkBox);
                    Log.i("checkBox", "OK");
                }
            });
        }
    }
}
