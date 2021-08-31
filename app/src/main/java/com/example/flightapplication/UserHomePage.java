package com.example.flightapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flightapplication.Model.Route;
import com.example.flightapplication.Model.BookingDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class UserHomePage extends AppCompatActivity {
    private TextView name;
    private static final String TAG = "UserHomePage";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDatesetListener;
    private Button viewProfile;
    private Spinner toSpinner;
    private Spinner fromSpinner;
    private ArrayList<Route> arrRoutes;
    private ArrayList<String> routeTo;
    private ArrayList<String> routeFrom;
    private DatabaseReference mRoutes, databaseReference;
    private ProgressDialog progressDialog;
    private int choosenTo;
    private int choosenFrom;
    AppCompatButton btnLogOut;
    FirebaseAuth mAuth;

    TextView tvDate;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home_page);
        // mDisplayDate = (TextView) findViewById(R.id.buttonDate);
        btnLogOut = findViewById(R.id.buttonLogOut);

        viewProfile = (Button) findViewById(R.id.buttonUser);
        viewProfile.setOnClickListener(v -> openProfile());

        mAuth = FirebaseAuth.getInstance();
        mRoutes = FirebaseDatabase.getInstance().getReference("RouteDetails");
        //databaseReference = FirebaseDatabase.getInstance().getReference("routesDetails");

        databaseReference = FirebaseDatabase.getInstance().getReference();

        toSpinner = (Spinner) findViewById(R.id.toSpinner);
        fromSpinner = (Spinner) findViewById(R.id.fromSpinner);

        progressDialog = new ProgressDialog(this);

        arrRoutes = new ArrayList<>();
        routeTo = new ArrayList<>();
        routeFrom = new ArrayList<>();

        name = (TextView) findViewById(R.id.textView4);

        tvDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UserHomePage.this
                        , android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , mDatesetListener
                        , year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDatesetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(TAG, "OnDateSet:date :" + day + "/" + (month + 1) + "/" + year);
                String date = day + "/" + (month + 1) + "/" + year;
                // String status="Journey Date";
                // mDisplayDate.setText(status+"\n"+date);
                mDisplayDate.setText(date);
            }
        };


//        String nameU = mAuth.getCurrentUser().getDisplayName();
//        name.setText(nameU);

//        if(mAuth.getCurrentUser() == null){
//            Intent loginIntent = new Intent(UserHomePage.this,Login_Page.class);
//            startActivity(loginIntent);
//            Toast.makeText(this, "Please Log in", Toast.LENGTH_SHORT).show();
//        }

        mRoutes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrRoutes.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        arrRoutes.add((Route) snapshot1.getValue(Route.class));
                    }

                    for (int i = 0; i < arrRoutes.size(); i++) {
                        routeTo.add(arrRoutes.get(i).getTo());
                        routeFrom.add(arrRoutes.get(i).getFrom());
                    }
                    Collections.sort(routeTo);
                    Collections.sort(routeFrom);
                    ArrayList<String> toArr = new ArrayList<>();
                    ArrayList<String> fromArr = new ArrayList<>();
                    String currentTo = routeTo.get(0);
                    String currentFrom = routeFrom.get(0);
                    toArr.add(currentTo);
                    fromArr.add(currentFrom);

                    for (int i = 1; i < routeTo.size(); i++) {
                        if (!currentTo.equals(routeTo.get(i))) {
                            toArr.add(routeTo.get(i));
                            currentTo = routeTo.get(i);
                        }
                    }
                    routeTo = toArr;
                    for (int i = 1; i < routeFrom.size(); i++) {
                        if (!currentFrom.equals(routeFrom.get(i))) {
                            fromArr.add(routeFrom.get(i));
                            currentFrom = routeFrom.get(i);
                        }
                    }
                    routeFrom = fromArr;

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, routeTo);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    toSpinner.setAdapter(adapter);
                    toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choosenTo = i;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            Toast.makeText(UserHomePage.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, routeFrom);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    fromSpinner.setAdapter(adapter2);
                    fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choosenFrom = i;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            Toast.makeText(UserHomePage.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserHomePage.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(UserHomePage.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(UserHomePage.this, Login_Page.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
                finish();

            }
        });
//        mDisplayDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(UserHomePage.this, android.R.style.Theme_Holo_Dialog_MinWidth, onDateSetListener, year, month, day);
//                datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
//                datePickerDialog.show();
//
//            }
//        });
//        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//
//                Log.d(TAG, "onDateSet: mm/dd/yy: " + month + "/" + dayOfMonth + "/" + year);
//                String date = month + "/" + dayOfMonth + "/" + year;
//                mDisplayDate.setText(date);
//            }
//       };
    }

    public void openProfile() {
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);
    }

    public void onClickBuy(View v) {
        searchFlight();

    }

    private void searchFlight() {

        String from = fromSpinner.getSelectedItem().toString().trim();
        String to = toSpinner.getSelectedItem().toString().trim();
        String date = tvDate.getText().toString().trim();


        if (TextUtils.equals(from, "FROM")) {
            Toast.makeText(this, "Please select departure place", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals(to, "TO")) {
            //password is empty
            Toast.makeText(this, "Please select destination place", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(date)) {
            //password is empty
            Toast.makeText(this, "Please select journey date", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Searching Fligts Please Wait...");
        progressDialog.show();
        BookingDetail route = new BookingDetail(from, to, date);
        FirebaseUser user1 = mAuth.getCurrentUser();
        databaseReference.child(user1.getUid()).child("BookingDetails").setValue(route);
        Intent intent = new Intent(UserHomePage.this, FlightActivity.class);

        intent.putExtra("FROM", from);
        intent.putExtra("TO", to);
        intent.putExtra("DATE", date);
        startActivity(intent);
        progressDialog.dismiss();

    }

}
