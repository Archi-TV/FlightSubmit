package com.example.ex.cells;

import androidx.annotation.NonNull;

public class AbsResultCell {
    public enum ViewType {
        RATING,
        BUTTON,
        CUSTOM_RATING
    }

    @NonNull
    private final ViewType viewType;

    AbsResultCell(@NonNull final ViewType viewType) {
        this.viewType = viewType;
    }

    @NonNull
    public ViewType getViewType() {
        return viewType;
    }

    @Override
    @SuppressWarnings({"SimplifiableIfStatement", "RedundantIfStatement"})
    public boolean equals(final Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    @NonNull
    public String toString() {
        return  "";
    }

}



