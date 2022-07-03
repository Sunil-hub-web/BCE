package com.bceclub.app.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.Models.ConnectionListModalClass;
import com.bceclub.app.R;

import java.util.ArrayList;

public class RequestSentAdapter extends RecyclerView.Adapter<RequestSentAdapter.RequestSendViewHolder> {

    ArrayList<ConnectionListModalClass.ResuestSend> resuestSends = new ArrayList<>();

    public RequestSentAdapter(ArrayList<ConnectionListModalClass.ResuestSend> resuestSends) {
        this.resuestSends = resuestSends;
    }

    @NonNull
    @Override
    public RequestSendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.request_sent, parent, false);
        RequestSendViewHolder viewHolder = new RequestSendViewHolder(listItem);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RequestSendViewHolder holder, int position) {
        holder.date.setText(resuestSends.get(position).getDate());
        holder.company.setText(resuestSends.get(position).getRequestTo().getCompany());
        holder.clubName.setText(resuestSends.get(position).getRequestTo().getClubName());
        holder.address.setText(resuestSends.get(position).getRequestTo().getAddress());
        if (resuestSends.get(position).getStatus().equals("0"))
            holder.status.setText("Request Pending");
        else {
            holder.status.setTextColor(R.color.green);
            holder.status.setText("Request Accepted");
        }
    }

    @Override
    public int getItemCount() {
        return resuestSends.size();
    }

    public void updateList(ConnectionListModalClass.ResuestSend resuestReceived) {
        //MemberItemList.clear();
        if (!resuestSends.contains(resuestReceived))
            resuestSends.add(resuestReceived);
        notifyDataSetChanged();
    }

    class RequestSendViewHolder extends RecyclerView.ViewHolder {
        TextView date, company, clubName, address, status;

        public RequestSendViewHolder(@NonNull View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.date);
            this.company = itemView.findViewById(R.id.companyName);
            this.clubName = itemView.findViewById(R.id.clubName);
            this.address = itemView.findViewById(R.id.Address);
            this.status = itemView.findViewById(R.id.requestStatus);
        }
    }
}
