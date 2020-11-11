package com.example.ex;

public final class State {
    private String text;
    private int food;
    private int flight;
    private int crew;
    private int aircraft;
    private int seat;
    private int people;

    @Override
    public String toString() {
        return "text: " + "\"" + text + "\""
                + ";  food: " + (food == -1 ? "null" : food) + ";  flight: " + flight
                + ";  crew: " + crew + ";  aircraft: " + aircraft + ";  seat: " + seat
                + ";  people: " + people;
    }

    void setAircraft(final int aircraft) {
        this.aircraft = aircraft;
    }

    void setCrew(final int crew) {
        this.crew = crew;
    }

    void setFlight(final int flight) {
        this.flight = flight;
    }

    void setFood(final int food) {
        this.food = food;
    }

    void setPeople(final int people) {
        this.people = people;
    }

    void setSeat(final int seat) {
        this.seat = seat;
    }

    void setText(final String text) {
        this.text = text;
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

    int getFood() {
        return food;
    }

    int getPeople() {
        return people;
    }

    int getSeat() {
        return seat;
    }

    String getText() {
        return text;
    }
}
