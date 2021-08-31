package com.example.flightapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login_Page extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private ProgressDialog loginProgress;
    private DatabaseReference mAdmin;
    private ArrayList<String> arrAdmin;
    private boolean isAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_login_page));

        loginEmail = findViewById(R.id.inputEmail4);
        loginPassword = findViewById(R.id.inputPassword3);
        loginButton = findViewById(R.id.buttonLogin);
        mAuth = FirebaseAuth.getInstance();
        mAdmin = FirebaseDatabase.getInstance().getReference("admins");
        loginProgress = new ProgressDialog(this);
        arrAdmin = new ArrayList<>();

        TextView btn = findViewById(R.id.textViewtoRegister);
        btn.setOnClickListener(v -> startActivity(new Intent(Login_Page.this, Register_Page.class)));


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    loginProgress.setTitle("Signing in");
                    loginProgress.setMessage("Please wait, Signing in");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();
                    login_user(email, password);


                } else {
                    Toast.makeText(Login_Page.this, "Please fill in the blank spaces", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void login_user(String email, String password) {
        mAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                     isAdmin = false;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        arrAdmin.add((String) snapshot1.getValue());
                    }
                    for (int i = 0; i < arrAdmin.size(); i++) {
                        if (email.equals(arrAdmin.get(i))) {
                            isAdmin = true;
                            Intent intent = new Intent(Login_Page.this, AdminPage.class);
                            startActivity(intent);
                        }
                    }
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loginProgress.dismiss();
                                Toast.makeText(Login_Page.this, "Login successful", Toast.LENGTH_SHORT).show();
                                if (!isAdmin) {
                                    Intent intent = new Intent(Login_Page.this, UserHomePage.class);
                                    startActivity(intent);
                                }
                            } else {
                                loginProgress.dismiss();
                                Toast.makeText(Login_Page.this, "Login failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login_Page.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
