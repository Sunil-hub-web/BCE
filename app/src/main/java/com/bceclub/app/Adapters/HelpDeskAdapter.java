package com.bceclub.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.Models.HelpDeskModalClass;
import com.bceclub.app.R;

import java.util.ArrayList;

public class HelpDeskAdapter extends RecyclerView.Adapter<HelpDeskAdapter.HelpDeskViewHolder> {

    ArrayList<HelpDeskModalClass.HelpDesk> helpDeskList = new ArrayList<>();

    public HelpDeskAdapter(ArrayList<HelpDeskModalClass.HelpDesk> helpDeskList) {
        this.helpDeskList = helpDeskList;
    }

    @NonNull
    @Override
    public HelpDeskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.help_desk_item, parent, false);
        HelpDeskViewHolder viewHolder = new HelpDeskViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HelpDeskViewHolder holder, int position) {
        holder.date.setText(helpDeskList.get(position).getTicketDate());
        holder.message.setText(helpDeskList.get(position).getMessage());
        holder.type.setText(helpDeskList.get(position).getTicketType());
        holder.relay.setText(helpDeskList.get(position).getReplyDate());
    }

    @Override
    public int getItemCount() {
        return helpDeskList.size();
    }

    public void updateHelpDesk(HelpDeskModalClass.HelpDesk item) {
        //MemberItemList.clear();
        if (!helpDeskList.contains(item))
            helpDeskList.add(item);
        notifyDataSetChanged();
    }

    public static class HelpDeskViewHolder extends RecyclerView.ViewHolder {

        TextView date, message, type, relay;

        public HelpDeskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.date);
            this.message = itemView.findViewById(R.id.helpDeskMessage);
            this.type = itemView.findViewById(R.id.helpDeskType);
            this.relay = itemView.findViewById(R.id.helpDeskReplay);
        }
    }
}
