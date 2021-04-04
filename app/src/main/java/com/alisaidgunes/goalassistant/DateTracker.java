package com.alisaidgunes.goalassistant;

public class DateTracker {

    private int id;
    private int goalisokay;
    private int point;

    public DateTracker(int id, int goalisokay,int point) {
        this.id = id;
        this.goalisokay = goalisokay;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoalisokay() {
        return goalisokay;
    }

    public void setGoalisokay(int goalisokay) {
        this.goalisokay = goalisokay;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }


    public DateTracker() {
    }

}
