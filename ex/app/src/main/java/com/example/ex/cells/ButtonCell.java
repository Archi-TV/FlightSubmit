package com.example.ex.cells;

import androidx.annotation.NonNull;

public class ButtonCell extends AbsResultCell {

    private final String text;

    public ButtonCell(@NonNull final ViewType viewType, final String text) {
        super(viewType);
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
