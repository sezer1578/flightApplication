package com.example.flightapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddAdmin extends AppCompatActivity {
    private Button back;
    private Button addAdmin;
    private DatabaseReference mUsers;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ArrayList<User> arrUser;
    private ArrayList<String> userNames;
    private Spinner spinner;
    private int choosenUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.addadminpage));

        mAuth = FirebaseAuth.getInstance();
        mUsers = FirebaseDatabase.getInstance().getReference("users");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        back = (Button) findViewById(R.id.buttonBack);
        back.setOnClickListener(v -> startActivity(new Intent(AddAdmin.this, AdminPage.class)));
        arrUser = new ArrayList<>();
        userNames = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.spinner);

        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrUser.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        arrUser.add((User) snapshot1.getValue(User.class));
                    }
                    for (int i = 0; i < arrUser.size(); i++) {
                        userNames.add(arrUser.get(i).getName() + " " + arrUser.get(i).getSurname());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, userNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choosenUser = i;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            Toast.makeText(AddAdmin.this, "Please choose a user", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddAdmin.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });

        addAdmin = (Button) findViewById(R.id.buttonAdd);
        addAdmin.setOnClickListener(v -> addsAdmin());
    }

    public void addsAdmin() {
        mDatabase.child("admins").child(userNames.get(choosenUser)).setValue(arrUser.get(choosenUser).getMail());
        System.out.println(arrUser.get(choosenUser).getMail());
    }
}
