package com.example.hdh.smgproject;

public class PT {
    int ptID;
    String ptYear;
    String ptMonth;
    String ptDay;
    String ptTrainer;
    String ptTime;

    public int getPtID() {
        return ptID;
    }

    public void setPtID(int ptID) {
        this.ptID = ptID;
    }

    public String getPtYear() {
        return ptYear;
    }

    public void setPtYear(String ptYear) {
        this.ptYear = ptYear;
    }

    public String getPtMonth() {
        return ptMonth;
    }

    public void setPtMonth(String ptMonth) {
        this.ptMonth = ptMonth;
    }

    public String getPtDay() {
        return ptDay;
    }

    public void setPtDay(String ptDay) {
        this.ptDay = ptDay;
    }

    public String getPtTrainer() {
        return ptTrainer;
    }

    public void setPtTrainer(String ptTrainer) {
        this.ptTrainer = ptTrainer;
    }

    public String getPtTime() {
        return ptTime;
    }

    public void setPtTime(String ptTime) {
        this.ptTime = ptTime;
    }

    public PT(int ptID, String ptYear, String ptMonth, String ptDay, String ptTrainer, String ptTime) {
        this.ptID = ptID;
        this.ptYear = ptYear;
        this.ptMonth = ptMonth;
        this.ptDay = ptDay;
        this.ptTrainer = ptTrainer;
        this.ptTime = ptTime;
    }
}
