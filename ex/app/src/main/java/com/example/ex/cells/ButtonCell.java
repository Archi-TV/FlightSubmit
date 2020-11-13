package com.example.ex.cells;

import androidx.annotation.NonNull;

public class ButtonCell extends AbsResultCell {

    private final String text;
    private final boolean enabled;

    public ButtonCell(@NonNull final ViewType viewType, final String text, final boolean enabled) {
        super(viewType);
        this.text = text;
        this.enabled = enabled;
    }

    public String getText(){
        return text;
    }

    public final boolean isEnabled(){
        return enabled;
    }
}
