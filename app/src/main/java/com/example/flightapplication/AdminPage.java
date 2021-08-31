package com.example.flightapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class AdminPage extends AppCompatActivity {
    private Button addRoute;
    private Button viewFlights;
    private Button viewProfile;
    private Button addAdmin;
    private TextView adminName;
    private FirebaseAuth mAuth;
    AppCompatButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.admin_homepage));
        mAuth = FirebaseAuth.getInstance();

        addRoute = (Button) findViewById(R.id.buttonAddRoute);
        addRoute.setOnClickListener(v -> openAddRoute());

        viewFlights = (Button) findViewById(R.id.buttonViewFlight);
        viewFlights.setOnClickListener(v -> openViewFlights());

        addAdmin = (Button) findViewById(R.id.buttonAddAdmin);
        addAdmin.setOnClickListener(v -> openAddAdmin());

        viewProfile = (Button) findViewById(R.id.buttonUser2);
        viewProfile.setOnClickListener(v -> openProfile());


        adminName = (TextView) findViewById(R.id.textView5);
        //String nameU = mAuth.getCurrentUser().getDisplayName();
        //adminName.setText(nameU);

        logout = findViewById(R.id.buttonBackR);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(AdminPage.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(AdminPage.this,Login_Page.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
                finish();

            }
        });

    }

    public void openAddRoute() {
        startActivity(new Intent(this, AddRoute.class));
    }

    public void openViewFlights() {

        Intent intent = new Intent(AdminPage.this,ViewRouteActivity.class);
        startActivity(intent);
    }

    public void openProfile() {

    }

    public void openAddAdmin() {
        startActivity(new Intent(this, AddAdmin.class));
    }

}

