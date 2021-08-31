package com.example.flightapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ProfilePage extends AppCompatActivity {

    private Button backMenu;
    private Button saveChanges;
    AppCompatButton buttonTicket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.profile_page));

        backMenu = (Button) findViewById(R.id.buttonBackR);
        backMenu.setOnClickListener(v -> backSearch());

        saveChanges = (Button) findViewById(R.id.buttonAddR);
        saveChanges.setOnClickListener(v -> saveChange());
        buttonTicket = findViewById(R.id.buttonTicket);
        buttonTicket.setOnClickListener(view -> viewTicker());
    }

    private void viewTicker( ) {
        Intent intentTicker = new Intent(ProfilePage.this,TicketDetailActivity.class);
        startActivity(intentTicker);
    }

    public void backSearch(){
        Intent intent = new Intent(this, UserHomePage.class);
        startActivity(intent);
    }
    public void saveChange(){

    }
}
