package com.example.ex;

public class Tuple {
    private String title;
    private int rating;
    private boolean checked = false;
    private String checkBoxText;


    String getTitle() {
        return title;
    }

    void setTitle(final String title) {
        this.title = title;
    }

    int getRating() {
        return rating;
    }

    void setRating(final int rating) {
        this.rating = rating;
    }

    boolean isChecked() {
        return checked;
    }

    void setChecked(final boolean checked) {
        this.checked = checked;
    }

    String getCheckBoxText(){
        return checkBoxText;
    }

    void setCheckBoxText(final String text){
        checkBoxText = text;
    }
}