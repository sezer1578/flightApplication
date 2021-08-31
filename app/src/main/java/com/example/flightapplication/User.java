package com.example.flightapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class User {
    private String mail;
    private String name;
    private String number;
    private String surname;

    public User(String mail, String name, String number, String surname) {
        this.mail = mail;
        this.name = name;
        this.number = number;
        this.surname = surname;
    }

    public User() {
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}