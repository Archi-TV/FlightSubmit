package com.example.ex;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex.cells.AbsResultCell;
import com.example.ex.cells.ButtonCell;
import com.example.ex.cells.Tuple;
import com.example.ex.holder.AbsResultHolder;
import com.example.ex.holder.RecyclerViewHolderButton;
import com.example.ex.holder.RecyclerViewViewHolder;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Activity context;
    private final ArrayList<AbsResultCell> tupleArrayList = new ArrayList<>();
    private final MainViewModel viewModel;
    private final State state;
    private final AbsResultCell.ViewType[] viewTypeValues = AbsResultCell.ViewType.values();

    RecyclerViewAdapter(final Activity context, final MainViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        state = viewModel.getUserMutableLiveData().getValue();
        populateList();
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
                return new RecyclerViewHolderButton(rootView, viewModel);
            case CUSTOM_RATING:
                rootView = LayoutInflater.from(context)
                        .inflate(R.layout.adapter_item_with_custom_ratingbar,parent,false);
                break;
            default:
                throw new IllegalArgumentException("Unknown ViewType: " + viewType);
        }
        return new RecyclerViewViewHolder(rootView, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final AbsResultCell tuple = tupleArrayList.get(position);
        ((AbsResultHolder) holder).bind(tuple);
    }



    @Override
    public int getItemCount() {
        return tupleArrayList.size();
    }


    private void populateList(){
        tupleArrayList.clear();
        addTuple("How crowded was the flight?", state.getPeople(), false, 0);
        addTuple("How do you rate the aircraft?", state.getAircraft(), false, 1);
        addTuple("How do you rate the seats?", state.getSeat(), false, 2);
        addTuple("How do you rate the crew?", state.getCrew(), false, 3);
        addTuple("How do you rate the food?", state.getFood(), true, 4);

        final ButtonCell cell = new ButtonCell(AbsResultCell.ViewType.BUTTON);
        tupleArrayList.add(cell);
    }

    private void addTuple(final String title, final int rating, final boolean flag, final int index){

        final AbsResultCell.ViewType viewType = index == 0
                ? AbsResultCell.ViewType.CUSTOM_RATING : AbsResultCell.ViewType.RATING;
        final Tuple tuple = new Tuple(title, rating, state.getFood() == -1, flag,
                index, viewType);
        tupleArrayList.add(tuple);
    }

    @Override
    public int getItemViewType(final int position) {
        return tupleArrayList.get(position).getViewType().ordinal();
    }

}