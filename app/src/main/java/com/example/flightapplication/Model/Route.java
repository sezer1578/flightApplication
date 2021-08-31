package com.example.flightapplication.Model;

import java.util.Comparator;

public class Route {
    private String routeId;
    private String from;
    private String to;
    private String price;
    private String date;
    private String time;
    private String toTime;
    private String status;
    private String duration;

    public Route( ) {
    }

    public Route(String routeId, String from, String to, String price, String date,String time,String toTime,String status) {
        this.routeId = routeId;
        this.from = from;
        this.to = to;
        this.price = price;
        this.date = date;
        this.time = time;
        this.toTime = toTime;
        this.status = status;
    }

    public String getDuration( ) {
        return duration;

    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getToTime( ) {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getTime( ) {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus( ) {
        return status;
    }

    public void setStatus(String time) {
        this.status = status;
    }

    public String getRouteId( ) {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
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


    public String getPrice( ) {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate( ) {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public  static final Comparator<Route> BY_TIME_ASCENDING = new Comparator<Route>() {
        @Override
        public int compare(Route o1, Route o2) {

            //ascending
            return o1.getTime().compareTo(o2.getTime());

        }
    };
    public  static final Comparator<Route> BY_TIME_DESCENDING = new Comparator<Route>() {
        @Override
        public int compare(Route o1, Route o2) {

            //descending
            return o2.getTime().compareTo(o1.getTime());

        }
    };
}
