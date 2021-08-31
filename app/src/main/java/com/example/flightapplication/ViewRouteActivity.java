package com.example.flightapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flightapplication.Model.Route;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewRouteActivity extends AppCompatActivity {
    private ListView listViewRoutes;
    private DatabaseReference databaseRoute;
    private List<Route> routeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_route);

        routeList = new ArrayList<>();

        listViewRoutes = findViewById(R.id.listViewRouteDetails);
        databaseRoute = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase.getInstance().getReference("RouteDetails")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        routeList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Route route = snapshot.getValue(Route.class);
                                routeList.add(route);
                            }
                            RouteList adapter = new RouteList(ViewRouteActivity.this, routeList);
                            listViewRoutes.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }
}
