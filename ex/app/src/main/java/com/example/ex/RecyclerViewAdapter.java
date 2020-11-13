package com.example.ex;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ex.cells.AbsResultCell;
import com.example.ex.holder.AbsResultHolder;
import com.example.ex.holder.RecyclerViewHolderButton;
import com.example.ex.holder.RecyclerViewViewHolder;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Activity context;
    private final ArrayList<AbsResultCell> cellArrayList;
    private final AbsResultCell.ViewType[] viewTypeValues = AbsResultCell.ViewType.values();
    private final TripListActionListener tripListActionListener;

    RecyclerViewAdapter(final Activity context, ArrayList<AbsResultCell> tupleArrayList,
                        @NonNull final TripListActionListener tripListActionListener) {
        this.tripListActionListener = tripListActionListener;
        this.context = context;
        this.cellArrayList = tupleArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View rootView;

        final AbsResultCell.ViewType type = viewTypeValues[viewType];
        switch (type) {
            case RATING:
                rootView = LayoutInflater.from(context)
                        .inflate(R.layout.adapter_item_with_star_ratingbar,parent,false);
                break;
            case BUTTON:
                rootView = LayoutInflater.from(context)
                        .inflate(R.layout.adapter_item_text_button_progress,parent,false);
                return new RecyclerViewHolderButton(rootView, tripListActionListener);
            case CUSTOM_RATING:
                rootView = LayoutInflater.from(context)
                        .inflate(R.layout.adapter_item_with_custom_ratingbar,parent,false);
                break;
            default:
                throw new IllegalArgumentException("Unknown ViewType: " + viewType);
        }
        return new RecyclerViewViewHolder(rootView, tripListActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final AbsResultCell cell = cellArrayList.get(position);
        ((AbsResultHolder) holder).bind(cell);
    }



    @Override
    public int getItemCount() {
        return cellArrayList.size();
    }


    @Override
    public int getItemViewType(final int position) {
        return cellArrayList.get(position).getViewType().ordinal();
    }

}
