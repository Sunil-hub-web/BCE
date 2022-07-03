package com.bceclub.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.Models.GuestListModalClass;
import com.bceclub.app.R;

import java.util.ArrayList;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestListViewHolder>{

    ArrayList<GuestListModalClass.Guest> guestList = new ArrayList<>();

    public GuestListAdapter(ArrayList<GuestListModalClass.Guest> guestList) {
        this.guestList = guestList;
    }

    @NonNull
    @Override
    public GuestListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.guest_list_item, parent, false);
        GuestListViewHolder viewHolder = new GuestListViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GuestListViewHolder holder, int position) {
        holder.name.setText(guestList.get(position).getGuestDetails().getName());
        holder.phone.setText(guestList.get(position).getGuestDetails().getMobile());
        holder.business.setText(guestList.get(position).getGuestDetails().getBusinessName());
        holder.date.setText(guestList.get(position).getDate());
        //holder.addr.setText(guestList.get(position).getGuestDetails().getName());
    }

    @Override
    public int getItemCount() {
        return guestList.size();
    }

    public void updateGuestList(GuestListModalClass.Guest item){
        if (!guestList.contains(item))
            guestList.add(item);
        notifyDataSetChanged();
    }

    class GuestListViewHolder extends RecyclerView.ViewHolder{
        TextView name, business, date, phone, addr;

        public GuestListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.business = itemView.findViewById(R.id.business);
            this.date = itemView.findViewById(R.id.date);
            this.phone = itemView.findViewById(R.id.phone);
            this.addr = itemView.findViewById(R.id.address);
        }
    }
}
