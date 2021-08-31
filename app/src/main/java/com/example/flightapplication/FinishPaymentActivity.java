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

public class FinishPaymentActivity extends AppCompatActivity {

    private AppCompatButton buttonViewDetail;
    private TextView from,to,price,date;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_payment);


        from=(TextView)findViewById(R.id.textViewfromTicket);
        to=(TextView)findViewById(R.id.textViewtoTicket);
        price=(TextView)findViewById(R.id.textViewPriceTicket);
        date=(TextView)findViewById(R.id.textViewdateTicket);
        buttonViewDetail=(AppCompatButton) findViewById(R.id.btnHome);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("FlightDetails");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSsnapshot) {
                String flightDate = dataSsnapshot.child("date").getValue().toString();
                String flightPrice = dataSsnapshot.child("price").getValue().toString();
                String flightfrom = dataSsnapshot.child("from").getValue().toString();
                String flightto = dataSsnapshot.child("to").getValue().toString();


                from.setText("From: " +flightfrom);
                to.setText("To:" + flightto);
                price.setText("Price: " + flightPrice);
                date.setText("Date: " + flightDate);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("status").setValue("Booked");
        buttonViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FinishPaymentActivity.this,TicketDetailActivity.class);
                startActivity(intent);
            }
        });

    }
}