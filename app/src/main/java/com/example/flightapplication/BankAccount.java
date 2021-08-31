package com.example.flightapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BankAccount extends AppCompatActivity {
    private EditText bankAccount;
    private EditText IBAN;
    private Button payAccount;
    private String price;
    private TextView viewPrice;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account);
        Intent intent = getIntent();
        price = intent.getStringExtra("price");
        viewPrice = findViewById(R.id.viewPrice);
        viewPrice.setText(price+"₺");
        bankAccount = findViewById(R.id.bank);
        IBAN = findViewById(R.id.account);
        payAccount = findViewById(R.id.buttonBankTransfer);

        payAccount.setOnClickListener(v -> startPayment());

    }

    private void startPayment() {
        //if both of the inputs are empty
        if (bankAccount.getText().toString().isEmpty() | IBAN.getText().toString().isEmpty()){
            Toast.makeText(BankAccount.this, "Fill empty spaces", Toast.LENGTH_SHORT).show();
        }else if(!IBAN.getText().toString().startsWith("TR")){
            Toast.makeText(BankAccount.this, "Add TR to IBAN", Toast.LENGTH_SHORT).show();
        }
        // IBAN's length is 26
        else if(IBAN.getText().toString().length()!=26){
            Toast.makeText(BankAccount.this, "Wrong IBAN", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(BankAccount.this, "Payment completed!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BankAccount.this, FinishPaymentActivity.class);
            startActivity(intent);
        }
    }

}
