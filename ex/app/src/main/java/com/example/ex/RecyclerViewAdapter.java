package com.example.ex;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity context;
    private ArrayList<Tuple> tupleArrayList;
    private boolean flag = true;

    RecyclerViewAdapter(final Activity context, final ArrayList<Tuple> tupleArrayList) {
        this.context = context;
        this.tupleArrayList = tupleArrayList;
    }

    ArrayList<Tuple> getTupleArrayList(){
        return tupleArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View rootView;
        if (flag)
        {
            flag = false;
            rootView = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_item_with_custom_ratingbar,parent,false);
        } else {
            rootView = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_item_with_star_ratingbar,parent,false);
        }

        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Tuple tuple = tupleArrayList.get(position);
        final RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;
        viewHolder.txtView_title.setText(tuple.getTitle());

        setRating(tuple, viewHolder);
        setCheckBox(tuple, viewHolder);
    }

    private void setRating(final Tuple tuple, final RecyclerViewViewHolder viewHolder){
        viewHolder.ratingBar.setRating(tuple.getRating());
        viewHolder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar,
                                        final float rating, final boolean fromUser) {
                tuple.setRating((int)rating);
                Log.i("rating", Integer.toString((int)rating));
            }
        });
    }

    private void setCheckBox(final Tuple tuple, final RecyclerViewViewHolder viewHolder){

        if(tuple.getCheckBoxText() == null){
            viewHolder.checkBox.setVisibility(View.GONE);
        } else{
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setText(tuple.getCheckBoxText());


            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    viewHolder.ratingBar.setRating(0);
                    viewHolder.ratingBar.setEnabled(!viewHolder.checkBox.isChecked());
                    tuple.setChecked(viewHolder.checkBox.isChecked());
                    Log.i("checkBox", "OK");
                }
            });

            if (tuple.isChecked()) {
                viewHolder.checkBox.performClick();
            }
        }
    }


    @Override
    public int getItemCount() {
        return tupleArrayList.size();
    }

    private final class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        private final RatingBar ratingBar;
        private final TextView txtView_title;
        private final CheckBox checkBox;

        private RecyclerViewViewHolder(@NonNull final View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            txtView_title = itemView.findViewById(R.id.txtView_title);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}