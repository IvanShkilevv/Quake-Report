package com.example.android.quakereportreal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquake {
    private String place;
    private Double magnitude;
    private Long date;
    private String URL;

    public Earthquake(String place, Double magnitude, Long date, String URL) {
        this.place = place;
        this.magnitude = magnitude;
        this.date = date;
        this.URL = URL;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getURL() {
        return URL;
    }

    public String formatDate(Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormatter.format(dateObject);
    }

    public String formatTime(Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a");
        return dateFormatter.format(dateObject);
    }

    public String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }


}
