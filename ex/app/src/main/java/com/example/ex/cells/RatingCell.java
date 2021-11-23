package com.example.ex.cells;

public class RatingCell extends AbsResultCell {
    private final String title;
    private final int rating;
    private final boolean checked;
    private final String checkBoxText;
    private final boolean needsCheck;
    private final int index;
    private final boolean enabled;


    public RatingCell(final String title, final int rating,
                      final boolean checked, final boolean needsCheck, int index,
                      ViewType viewType, final boolean enabled){
        super(viewType);
        this.title = title;
        this.enabled = enabled;
        this.rating = rating;
        this.checked = checked;
        this.needsCheck = needsCheck;
        if (needsCheck){
            checkBoxText = "  There were no food";
        } else{
            checkBoxText = "";
        }
        this.index = index;
    }

    public final boolean isEnabled(){
        return enabled;
    }

    public final int getIndex(){
        return index;
    }

    public final boolean checkIsNeeded(){
        return needsCheck;
    }

    public final String getTitle() {
        return title;
    }

    public final int getRating() {
        return rating;
    }

    public final boolean isChecked() {
        return checked;
    }

    public final String getCheckBoxText(){
        return checkBoxText;
    }
}