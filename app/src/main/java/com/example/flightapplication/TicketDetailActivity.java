package com.example.flightapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TicketDetailActivity extends AppCompatActivity {
    private TextView date,detailFrom,detailTo,detailStatus,fromTime,toTime,routeId,price,ticketDate,i,j,k,l;
    private DatabaseReference databaseReference1,databaseReference2,databaseReference3;
    private FirebaseAuth firebaseAuth;
    private AppCompatButton homeButton,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        date = findViewById(R.id.routeDetailDate1);
        detailFrom = findViewById(R.id.routeDetailFrom1);
        detailTo = findViewById(R.id.roteDetailTo1);
        detailStatus = findViewById(R.id.status);
        fromTime = findViewById(R.id.bookingDetailFromTime);
        toTime = findViewById(R.id.bookingDetailToTime);
        routeId = findViewById(R.id.bookingDetailrouteId);
        price = findViewById(R.id.ticketDetailPrice);
        ticketDate = findViewById(R.id.ticketDetailDate);

        j=(TextView)findViewById(R.id.customerDetailName1);
        k=(TextView)findViewById(R.id.customerDetailEmail1);
        l=(TextView)findViewById(R.id.customerDetailPhone1);

        homeButton = findViewById(R.id.ticketDetailbtnHome);
        cancel = findViewById(R.id.cancelRequest);
        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference1 = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("FlightDetails");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("CustomerDetails");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String flightDate = dataSnapshot.child("date").getValue().toString();
                String flightfrom = dataSnapshot.child("from").getValue().toString();
                String flightto = dataSnapshot.child("to").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();


                String flightfromTime = dataSnapshot.child("time").getValue().toString();
                String flighttoTime = dataSnapshot.child("toTime").getValue().toString();
                String flightprice = dataSnapshot.child("price").getValue().toString();
                String flightrouteId = dataSnapshot.child("routeId").getValue().toString();

                date.setText("Date: " + flightDate);
                detailFrom.setText("From: " +flightfrom);
                detailTo.setText("To: " + flightto);
                detailStatus.setText("Status: "+status);
                fromTime.setText("From Time: " + flightfromTime);
                toTime.setText("To Time: " + flighttoTime);
                routeId.setText("Ticket ID: " + flightrouteId);
                price.setText("Price: " + flightprice);
                ticketDate.setText("Date: " +flightDate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String customerDetailName=dataSnapshot.child("cus_name").getValue().toString();
                String customerDetailEmail1=dataSnapshot.child("cus_email").getValue().toString();
                String customerDetailPhone=dataSnapshot.child("cus_phone").getValue().toString();

                j.setText(" Customer_Name: "+customerDetailName);
                k.setText(" Customer_Email: "+customerDetailEmail1);
                l.setText(" Customer_Phone: "+customerDetailPhone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketDetailActivity.this,UserHomePage.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference1.child("status").setValue("Approved");
                Intent intent = new Intent(TicketDetailActivity.this,TicketDetailActivity.class);
                startActivity(intent);
            }
        });










    }
}