package com.example.flightapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightapplication.Interface.ItemClickListener;
import com.example.flightapplication.Model.Route;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FlightActivity extends AppCompatActivity implements ItemClickListener {

    private static final String TAG = "FlightActivity";
    private RecyclerView recyclerView;
    private FlightAdapter adapter;
    private List<Route> routeList;
    private ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;

    LinearLayoutManager mLayoutMaganer;//for sorting
    SharedPreferences mSharedPref, pref;  //for saving sort settings
    TextView sortPrice, sortTime;

    String fromFlight,toFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        routeList = new ArrayList<>();
        adapter = new FlightAdapter(this, routeList);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener((ItemClickListener) this);
        sortPrice = findViewById(R.id.txt_sort_price);
        sortTime = findViewById(R.id.txt_sort_time);

        //Firebase
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


         fromFlight = getIntent().getStringExtra("FROM");
         toFlight = getIntent().getStringExtra("TO");
        String date = getIntent().getStringExtra("DATE");


        //sorting codes
        mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = mSharedPref.getString("Sort", "highest"); // where if no setting is selected highest default


        if (mSorting.equals("highest")) {
            mLayoutMaganer = new LinearLayoutManager(this);
            //this will load the items from bottom means highest first
            mLayoutMaganer.setReverseLayout(true);
            mLayoutMaganer.setStackFromEnd(true);

        } else if (mSorting.equals("cheapest")) {
            mLayoutMaganer = new LinearLayoutManager(this);
            //this will load the items from bottom means cheapest first
            mLayoutMaganer.setReverseLayout(false);
            mLayoutMaganer.setStackFromEnd(false);

        }
        recyclerView.setLayoutManager(mLayoutMaganer);


        //sorting Price
        sortPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(FlightActivity.this, "deneme", Toast.LENGTH_SHORT).show();

                showSortDialog();
            }
        });

        //sorting Time
        sortTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showSortDialogforTime();
            }
        });


        FirebaseDatabase.getInstance().getReference("RouteDetails")
                .orderByChild("from")
                .equalTo(fromFlight)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        routeList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Route route = snapshot.getValue(Route.class);
                                routeList.add(route);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        FirebaseDatabase.getInstance().getReference().child("RouteDetails")
                                .orderByChild("to")
                                .equalTo(toFlight)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        routeList.clear();
                                        if (dataSnapshot.exists()) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                Route route = snapshot.getValue(Route.class);
                                                routeList.add(route);
                                            }
                                            adapter.notifyDataSetChanged();


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(FlightActivity.this, "Firebase Database Error", Toast.LENGTH_LONG).show();
                                    }
                                });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(FlightActivity.this, "Firebase Database Error", Toast.LENGTH_LONG).show();
                    }
                });


    }

    private void showSortDialogforTime( ) {

        String[] sortOption = {"Ascending", "Descending"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort By Time")
                .setIcon(R.drawable.ic_action_sort)
                .setItems(sortOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            //sort by highest
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "ascending"); // where sort is key & highest is value
                            editor.apply();
                            recreate();


                        } else if (i == 1) {
                            //sort by cheapest
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "descending"); // where sort is key & highest is value
                            editor.apply();
                            recreate();
                        }
                    }
                });
        builder.show();
    }

    private void showSortDialog( ) {

        String[] sortOption = {"Highest", "Cheapest"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by")
                .setIcon(R.drawable.ic_action_sort)
                .setItems(sortOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i == 0) {
                            //sort by highest
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "highest"); // where sort is key & highest is value
                            editor.apply();
                            recreate();


                        } else if (i == 1) {
                            //sort by cheapest
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "cheapest"); // where sort is key & highest is value
                            editor.apply();
                            recreate();
                        }
                    }
                });
        builder.show();
    }


    ValueEventListener valueEventListener = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            routeList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Route route = snapshot.getValue(Route.class);
                    routeList.add(route);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    @Override
    public void Onclick(View view, int position) {

        Route routeDetail = routeList.get(position);

        String routeId = routeDetail.getRouteId();
        String from = routeDetail.getFrom();
        String to = routeDetail.getTo();
        String price = routeDetail.getPrice();
        String date = routeDetail.getDate();
        String fromTime = routeDetail.getTime();
        String toTime = routeDetail.getToTime();
        String status = routeDetail.getStatus();
        Route route = new Route(routeId,from,to,price,date,fromTime,toTime,status);
        FirebaseUser user1 = firebaseAuth.getCurrentUser();
        databaseReference.child(user1.getUid()).child("FlightDetails").setValue(route);

        Intent intent = new Intent(FlightActivity.this,FlightDetails.class);
        intent.putExtra("fromm",from);
        intent.putExtra("too",to);
        startActivity(intent);



    }


}