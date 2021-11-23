package com.example.ex;

public final class State {
    private String text;
    private int food;
    private int flight;
    private int crew;
    private int aircraft;
    private int seat;
    private int people;
    private boolean enabled = true;

    /**
     * String state
     * @return state info
     */
    @Override
    public String toString() {
        return "text: " + "\"" + text + "\""
                + ";  food: " + (food == -1 ? "null" : (food + 1)) + ";  flight: " + (flight + 1)
                + ";  crew: " + (crew + 1) + ";  aircraft: " + (aircraft + 1) + ";  seat: " + (seat + 1)
                + ";  people: " + (people + 1);
    }

    public void setAircraft(final int aircraft) {
        this.aircraft = aircraft;
    }

    public void setCrew(final int crew) {
        this.crew = crew;
    }

    void setFlight(final int flight) {
        this.flight = flight;
    }

    public void setFood(final int food) {
        this.food = food;
    }

    public void setPeople(final int people) {
        this.people = people;
    }

    public void setSeat(final int seat) {
        this.seat = seat;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    int getAircraft() {
        return aircraft;
    }

    int getCrew() {
        return crew;
    }

    int getFlight() {
        return flight;
    }

    public int getFood() {
        return food;
    }

    int getPeople() {
        return people;
    }

    int getSeat() {
        return seat;
    }

    public String getText() {
        return text;
    }

    public boolean isEnabled(){
        return enabled;
    }
}
