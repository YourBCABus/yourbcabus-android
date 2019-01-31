package com.yourbcabus.yourbcabus_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    private Context context;
    private List<Bus> busList;

    public BusAdapter(Context context, List<Bus> busList) {
        this.context = context;
        this.busList = busList;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, null);
        return new BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder busViewHolder, int i) {
        Bus bus = busList.get(i);

        busViewHolder.textViewName.setText(bus.getName());
        busViewHolder.textViewLocation.setText(bus.getLocation());
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewLocation;

        public BusViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.bus_name);
            textViewLocation = itemView.findViewById(R.id.bus_location);

        }
    }

    public void clear() {
        busList.clear();
        notifyDataSetChanged();
    }
}