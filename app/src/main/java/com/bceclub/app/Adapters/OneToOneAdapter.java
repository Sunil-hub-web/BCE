package com.bceclub.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.Models.OneToOneSend_Model;
import com.bceclub.app.R;

import java.util.ArrayList;

public class OneToOneAdapter extends RecyclerView.Adapter<OneToOneAdapter.ViewHolder> {

    Context context;
    ArrayList<OneToOneSend_Model> oneToOnemodels;

    OneToOneAdapter(Context context,ArrayList<OneToOneSend_Model> oneToOne_models){

        this.context = context;
        this.oneToOnemodels = oneToOne_models;
    }
    @NonNull
    @Override
    public OneToOneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onetoone_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OneToOneAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return oneToOnemodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Created_On,Meeting_On,Remark,clientName,serviceProvide,locationName,locationDetails;
        AutoCompleteTextView BusinessStatus;
        Button btn_edit,btn_submit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Created_On = itemView.findViewById(R.id.Created_On);
            BusinessStatus = itemView.findViewById(R.id.BusinessStatus);
            Meeting_On = itemView.findViewById(R.id.Meeting_On);
            Remark = itemView.findViewById(R.id.Remark);
            clientName = itemView.findViewById(R.id.clientName);
            serviceProvide = itemView.findViewById(R.id.serviceProvide);
            locationName = itemView.findViewById(R.id.locationName);
            locationDetails = itemView.findViewById(R.id.locationDetails);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_submit = itemView.findViewById(R.id.btn_submit);

            String[] itemNamesbank = context.getResources().getStringArray(R.array.ReceiveList);
            ArrayAdapter<String> bankaccountadapter = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itemNamesbank);
            bankaccountadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            BusinessStatus.setAdapter(bankaccountadapter);
        }
    }
}
