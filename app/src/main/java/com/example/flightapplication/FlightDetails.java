package com.example.flightapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.flightapplication.Interface.ItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FlightDetails extends AppCompatActivity{

     TextView to,from,date,price,fromTime,toTime;
     DatabaseReference databaseReference;
     FirebaseAuth firebaseAuth;
     AppCompatButton btnPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_details);

        to = (TextView) findViewById(R.id.text_view_toDetail);
        from = (TextView) findViewById(R.id.text_view_fromDetail);
        date = (TextView) findViewById(R.id.text_view_dateDetail);
        price = (TextView) findViewById(R.id.text_view_priceDetail);
        fromTime = (TextView) findViewById(R.id.text_view_fromtimeDetail);
        toTime = (TextView) findViewById(R.id.text_view_toTimeDetail);
        btnPay =findViewById(R.id.btn_pay);

/*
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlightDetails.this,FlightActivity.class);
                startActivity(intent);
                finish();
            }
        });

 */




        final String fromm = getIntent().getStringExtra("fromm");
        final String too = getIntent().getStringExtra("too");
        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("FlightDetails");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String flightFrom = dataSnapshot.child("from").getValue().toString();
                String flightTo = dataSnapshot.child("to").getValue().toString();
                String flightDate = dataSnapshot.child("date").getValue().toString();
                String flightPrice = dataSnapshot.child("price").getValue().toString();
                String flightfromTime = dataSnapshot.child("time").getValue().toString();
                String flighttoTime = dataSnapshot.child("toTime").getValue().toString();


                from.setText("From: " + flightFrom);
                to.setText("To: " + flightTo);
                date.setText("Date: " + flightDate);
                price.setText("Price: " + flightPrice);
                fromTime.setText("From Time: " + flightfromTime);
                toTime.setText("To Time: " + flighttoTime );
                String status = "Booked";
                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(FlightDetails.this,ChoosePayment.class);
                        intent.putExtra("price",flightPrice);
                        intent.putExtra("date",flightDate);
                        intent.putExtra("from",flightFrom);
                        intent.putExtra("status",status);
                        intent.putExtra("to",flightTo);
                        intent.putExtra("fromTime",flightfromTime);
                        intent.putExtra("toTime",flighttoTime);
                        startActivity(intent);
                    }
                });








            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       ///System.out.println(toFlight);



    }

}