package com.example.ex.holder;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ex.cells.AbsResultCell;

public abstract class AbsResultHolder extends RecyclerView.ViewHolder {


    public AbsResultHolder(@NonNull final View itemView) {
        super(itemView);
    }

    public abstract void bind(@NonNull final AbsResultCell cell);

}
