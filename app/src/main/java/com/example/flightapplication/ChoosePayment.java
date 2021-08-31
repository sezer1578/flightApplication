package com.example.flightapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChoosePayment extends AppCompatActivity {
    private Button creditCard;
    private Button bankAccount;
    private String price,date,from,to,fromTime,toTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.choosepayment));

        creditCard = findViewById(R.id.credit);
        bankAccount = findViewById(R.id.bank);
        Intent intent = getIntent();
        price = intent.getStringExtra("price");
        date = intent.getStringExtra("date");
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");
        fromTime = intent.getStringExtra("fromTime");
        toTime = intent.getStringExtra("toTime");
        creditCard.setOnClickListener(v -> openCreditCardPage());
        bankAccount.setOnClickListener(v -> openBankAccountPage());



    }

    private void openBankAccountPage() {
        Intent intent = new Intent(this, BankAccount.class);

        intent.putExtra("price",price);
        intent.putExtra("date",date);
        intent.putExtra("from",from);
        intent.putExtra("to",to);
        intent.putExtra("fromTime",fromTime);
        intent.putExtra("toTime",toTime);

        startActivity(intent);
    }

    private void openCreditCardPage() {
        Intent intent = new Intent(this, CreditCard.class);
        intent.putExtra("price",price);
        intent.putExtra("date",date);
        intent.putExtra("from",from);
        intent.putExtra("to",to);
        intent.putExtra("fromTime",fromTime);
        intent.putExtra("toTime",toTime);
        startActivity(intent);
    }
}
