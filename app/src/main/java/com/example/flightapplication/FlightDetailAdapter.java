package com.example.flightapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightapplication.Interface.ItemClickListener;
import com.example.flightapplication.Model.Route;

import java.util.List;

public class FlightDetailAdapter extends RecyclerView.Adapter<FlightDetailAdapter.FlightViewHolder>{

    private Context mCtx;
    private List<Route> routeList;
    private ItemClickListener clickListener;

    public FlightDetailAdapter(Context mCtx, List<Route> routeList) {
        this.mCtx = mCtx;
        this.routeList = routeList;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_flight_detail,parent,false);
        return new FlightViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Route route = routeList.get(position);
        holder.textViewFrom.setText("From: "+route.getFrom());
        holder.textViewTo.setText("To: " + route.getTo());
        holder.textViewDate.setText("Date: " + route.getDate());
        holder.textViewPrice.setText("Price: " + route.getPrice());
        holder.textviewFromTime.setText("From Time: " + route.getTime());
        holder.textViewtoTime.setText("To Time: " + route.getToTime());
        holder.textViewDuration.setText("Duration: " + route.getDuration());
    }

    @Override
    public int getItemCount( ) {
        return routeList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public class FlightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewFrom,textViewTo,textViewDate,textViewPrice,textviewFromTime,textViewtoTime,textViewDuration;


        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewFrom = itemView.findViewById(R.id.text_view_fromDetail);
            textViewTo = itemView.findViewById(R.id.text_view_toDetail);
            textViewDate = itemView.findViewById(R.id.text_view_dateDetail);
            textViewPrice = itemView.findViewById(R.id.text_view_priceDetail);
            textviewFromTime = itemView.findViewById(R.id.text_view_timeDetail);
            textViewtoTime = itemView.findViewById(R.id.text_view_timeToDetail);
            textViewDuration = itemView.findViewById(R.id.text_view_durationDetail);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            if(clickListener !=null) clickListener.Onclick(view,getAdapterPosition());

        }


    }

}


