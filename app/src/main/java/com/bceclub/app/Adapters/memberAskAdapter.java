package com.bceclub.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.Models.MemberAskListModal;
import com.bceclub.app.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class memberAskAdapter extends RecyclerView.Adapter<memberAskAdapter.memberAskViewHolder> {


    ArrayList<MemberAskListModal.AskHistory> memberAskList = new ArrayList<>();

    public memberAskAdapter(ArrayList<MemberAskListModal.AskHistory> memberAskList) {
        this.memberAskList = memberAskList;
    }

    @NonNull
    @Override
    public memberAskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.member_ask_item, parent, false);
        memberAskViewHolder viewHolder = new memberAskViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull memberAskViewHolder holder, int position) {
        holder.date.setText(memberAskList.get(position).getMeetingDate());
        holder.name.setText(memberAskList.get(position).getName());
        holder.department.setText(memberAskList.get(position).getDeparment());
        holder.company.setText(memberAskList.get(position).getConmpany());
        holder.reason.setText(memberAskList.get(position).getReason());

    }

    @Override
    public int getItemCount() {
        return memberAskList.size();
    }

    public void updateMemberAskList(MemberAskListModal.AskHistory memberAsk) {
        if (!memberAskList.contains(memberAsk))
            memberAskList.add(memberAsk);
        notifyDataSetChanged();
    }

    class memberAskViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextInputEditText name, department, company, reason;

        public memberAskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.date);
            this.name = itemView.findViewById(R.id.askName);
            this.department = itemView.findViewById(R.id.askDepartment);
            this.company = itemView.findViewById(R.id.askCompany);
            this.reason = itemView.findViewById(R.id.askReason);
        }
    }
}
