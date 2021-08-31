package com.example.flightapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flightapplication.Model.Route;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRoute extends AppCompatActivity {
    private Button back;
    private static final String TAG = "AddRoute";
    private Button addRoute;
    private EditText from;
    private EditText to;
    private EditText price;
    private TextView dateDisplay;
    private TextView buttonTime,toTime;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener,onTimeSetListener1;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    Route route;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.addroutepage));

        toTime = findViewById(R.id.txtToTime);
        buttonTime = findViewById(R.id.buttonTime);
        dateDisplay = (TextView) findViewById(R.id.buttonDate);
        from = (EditText) findViewById(R.id.inputFrom);
        to = (EditText) findViewById(R.id.inputTo);
        price = (EditText) findViewById(R.id.price);
        back = (Button) findViewById(R.id.buttonBackR);
        back.setOnClickListener(v -> startActivity(new Intent(this, AdminPage.class)));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        addRoute = (Button) findViewById(R.id.buttonAddR);
        addRoute.setOnClickListener(v -> addsRoute());
        progressDialog = new ProgressDialog(this);


        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddRoute.this,onTimeSetListener,hour,minute,false);
                timePickerDialog.show();


            }
        });

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHours, int selectedMinutes) {

                Log.d(TAG, "onHourSet: hh/mm" + selectedHours + ":" + selectedMinutes);
                String time = selectedHours + ":" + selectedMinutes;
                buttonTime.setText(new StringBuilder().append(time));
            }
        };




        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime1 = Calendar.getInstance();
                int hour = mcurrentTime1.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime1.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog1 = new TimePickerDialog(AddRoute.this,onTimeSetListener1,hour,minute,false);
                timePickerDialog1.show();
            }
        });

       onTimeSetListener1 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker1, int selectedHours, int selectedMinutes) {

                Log.d(TAG, "onHourSet: hh/mm" + selectedHours + ":" + selectedMinutes);
                String time1 = selectedHours + ":" + selectedMinutes;
                toTime.setText(new StringBuilder().append(time1));
            }
        };





        dateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddRoute.this, android.R.style.Theme_Holo_Dialog_MinWidth, onDateSetListener, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                datePickerDialog.show();

            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;

                Log.d(TAG, "onDateSet: mm/dd/yy: " + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
                dateDisplay.setText(date);
            }
        };

    }

    public void addsRoute() {


        String date = dateDisplay.getText().toString().trim();
        String from1 = from.getText().toString().trim();
        String to1 = to.getText().toString().trim();
        String price1 = price.getText().toString().trim();
        String time = buttonTime.getText().toString().trim();
        String toTime1 = toTime.getText().toString().trim();
        String status = "notBooked";
        String routeId = databaseReference.push().getKey();

        if (TextUtils.isEmpty(date)) {

            Toast.makeText(this, "Please Enter Journey Date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.equals(from1,"From")) {

            Toast.makeText(this, "Please Select Departure Place", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals(to1,"To")) {

            Toast.makeText(this, "Please Select Destination Place", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals(price1,"Price")) {

            Toast.makeText(this, "Please Select Bus Condition", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals(time,"Time")) {

            Toast.makeText(this, "Please Select Bus Condition", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals(toTime1,"ToTime")) {

            Toast.makeText(this, "Please Select Bus Condition", Toast.LENGTH_SHORT).show();
            return;
        }
        Route route = new Route(routeId,from1,to1,price1,date,time,toTime1,status);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child("RouteDetails").child(routeId).setValue(route);
        progressDialog.setMessage("Adding Buses Please Wait...");
        progressDialog.show();
        Intent intent=new Intent(getApplicationContext(),ViewRouteActivity.class);
        startActivity(intent);
        progressDialog.dismiss();



        /*String to1 = to.getText().toString();
        String from1 = from.getText().toString();
        String date1 = dateDisplay.getText().toString();
        String price1 = price.getText().toString();
        mDatabase.child("routes").child(from1 + " " + to1).child("from").setValue(from1);
        mDatabase.child("routes").child(from1 + " " + to1).child("to").setValue(to1);
        mDatabase.child("routes").child(from1 + " " + to1).child("date").setValue(date1);
        mDatabase.child("routes").child(from1 + " " + to1).child("price").setValue(price1);

         */
    }
}
