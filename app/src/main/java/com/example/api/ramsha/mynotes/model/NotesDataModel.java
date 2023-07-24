package com.example.api.ramsha.mynotes.model;

public class NotesDataModel {
    String heading,text,date;
    double[] location;

    public NotesDataModel(String heading, String text, String date,double[] location) {
        this.heading = heading;
        this.text = text;
        this.date = date;
        this.location=location;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
