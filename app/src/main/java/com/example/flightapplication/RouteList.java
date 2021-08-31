package com.example.flightapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flightapplication.Model.Route;

import java.util.List;

public class RouteList  extends ArrayAdapter<Route> {

    private Activity context;
    private List<Route> routeList;

    public RouteList(Activity context, List<Route> routeList) {
        super(context, R.layout.list_route, routeList);
        this.context = context;
        this.routeList = routeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_route, null, true);


        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.text_view_date);
        TextView textViewFrom = (TextView) listViewItem.findViewById(R.id.text_view_from);
        TextView textViewTo = (TextView) listViewItem.findViewById(R.id.text_view_to);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.text_view_price);
        TextView textViewTime = (TextView) listViewItem.findViewById(R.id.text_view_time);
        TextView textViewToTime = (TextView) listViewItem.findViewById(R.id.text_view_timeTo);
        TextView textViewDuration = (TextView) listViewItem.findViewById(R.id.text_view_durationRoute);



        Route route = routeList.get(position);

        textViewDate.setText("Journey Date      : "+route.getDate());
        textViewFrom.setText("Route From            : "+route.getFrom());
        textViewTo.setText("Route To                : "+route.getTo());
        textViewPrice.setText("Route Price    : "+route.getPrice());
        textViewTime.setText("From Time    : "+route.getTime());
        textViewToTime.setText("To Time    : "+route.getToTime());
        textViewDuration.setText("Duration: " + route.getDuration());

        System.out.println("Duration: " + route.getDuration());

        return listViewItem;
    }

}
