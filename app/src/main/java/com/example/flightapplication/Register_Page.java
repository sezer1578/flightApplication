package com.example.flightapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Page extends AppCompatActivity {
    private Button buttonSign;
    private EditText name;
    private EditText surname;
    private EditText mail;
    private EditText number;
    private EditText password;
    private EditText passwordA;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__page);
        name = (EditText) findViewById(R.id.inputFrom);
        surname = (EditText) findViewById(R.id.inputTo);
        mail = (EditText) findViewById(R.id.inputDate);
        number = (EditText) findViewById(R.id.inputNumber);
        password = (EditText) findViewById(R.id.inputPassword);
        passwordA = (EditText) findViewById(R.id.inputPasswordA);
        buttonSign = findViewById(R.id.buttonAddR);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        TextView btn = findViewById(R.id.textViewAccount);
        btn.setOnClickListener(v -> startActivity(new Intent(Register_Page.this, Login_Page.class)));


    }

    public void onClickButton(View view) {
        String name1 = name.getText().toString();
        String surname1 = surname.getText().toString();
        String mail1 = mail.getText().toString();
        String number1 = number.getText().toString();
        String password1 = password.getText().toString();
        String passwordA1 = passwordA.getText().toString();
        if (name1.isEmpty() || surname1.isEmpty() || mail1.isEmpty() || number1.isEmpty() || password1.isEmpty() || passwordA1.isEmpty()) {
            Toast.makeText(Register_Page.this, "Please fill in the blank spaces", Toast.LENGTH_LONG).show();
        } else {
            if (password1.length() >= 6) {
                if (password1.equals(passwordA1)) {
                    mAuth.createUserWithEmailAndPassword(mail1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mDatabase.child("users").child(number1).child("name").setValue(name1);
                                mDatabase.child("users").child(number1).child("surname").setValue(surname1);
                                mDatabase.child("users").child(number1).child("mail").setValue(mail1);
                                mDatabase.child("users").child(number1).child("number").setValue(number1);
                                Toast.makeText(Register_Page.this, "User successfully created", Toast.LENGTH_LONG).show();
                                Intent loginIntent = new Intent(Register_Page.this,Login_Page.class);
                                startActivity(loginIntent);
                            } else {
                                Toast.makeText(Register_Page.this, "User could not be created, Please try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Register_Page.this, "Passwords did not match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Register_Page.this, "Please make your password bigger than 5 character", Toast.LENGTH_SHORT).show();
            }
        }
    }
}