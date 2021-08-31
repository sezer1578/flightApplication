package com.example.flightapplication.Model;

public class CardDetail {
    private String bakiye;

    public CardDetail(String bakiye){
        this.bakiye = bakiye;
    }
    public CardDetail(){}

    public String getBakiye() {
        return bakiye;
    }

    public void setBakiye(String bakiye) {
        this.bakiye = bakiye;
    }

    @Override
    public String toString() {
        return "card{" +
                "bakiye='" + bakiye + '\'' +
                '}';
    }
}

