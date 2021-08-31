package com.example.flightapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.example.flightapplication.Model.CardDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreditCard extends AppCompatActivity {
    String priceCome,date,from,to,fromTime,toTime;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    TextView txtDes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);


        //Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(CreditCard.this, UserHomePage.class));
        }

        CardForm cardForm = findViewById(R.id.cardForm);
         txtDes = findViewById(R.id.payment_amount);
        Button btnPay = findViewById(R.id.btn_pay);

        Intent intent = getIntent();
        priceCome = intent.getStringExtra("price");
        date = intent.getStringExtra("date");
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");
        fromTime = intent.getStringExtra("fromTime");
        toTime = intent.getStringExtra("toTime");

        txtDes.setText(priceCome + "₺");

        btnPay.setText(String.format("Pay %s", txtDes.getText()));

      /*  cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {

                if(!card.validateCard() | !card.validateCVC() | !card.validateExpiryDate() | !card.validateNumber()){
                    Toast.makeText(CreditCard.this, "Card is not Valid", Toast.LENGTH_SHORT).show();
                }else if(card.getName().isEmpty()){
                    Toast.makeText(CreditCard.this, "Card Name is empty", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CreditCard.this, "Payment completed!", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(CreditCard.this, UserHomePage.class);
                    startActivity(intent2);
                }
            }
        });

       */
        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                String bakiye = txtDes.getText().toString().trim();
                CardDetail cardDetail = new CardDetail(bakiye);

                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.child(user.getUid()).child("PaymentDetails").setValue(cardDetail);

                Intent intent2 = new Intent(CreditCard.this, ConfirmActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("from",from);
                intent.putExtra("to",to);
                intent.putExtra("fromTime",fromTime);
                intent.putExtra("toTime",toTime);
                startActivity(intent2);
            }
        });

    }
}
