package com.example.flightapplication.Model;

public class BookingDetail {

    private String from;
    private String to;
    private String date;


    public BookingDetail( ) {
    }

    public BookingDetail(String from, String to, String date) {
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public String getFrom( ) {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo( ) {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate( ) {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
