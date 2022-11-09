package com.bceclub.app.Adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Models.OneToOneSend_Model;
import com.bceclub.app.Models.onetonedet_model;
import com.bceclub.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneToOneSendAdapter extends RecyclerView.Adapter<OneToOneSendAdapter.ViewHolder> {

    Context context;
    ArrayList<OneToOneSend_Model> oneToOnemodels;
    SimpleApi simpleApi;
    String user_id;

    public OneToOneSendAdapter(Context context, ArrayList<OneToOneSend_Model> oneToOne_models){

        this.context = context;
        this.oneToOnemodels = oneToOne_models;
    }
    @NonNull
    @Override
    public OneToOneSendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onetoone_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OneToOneSendAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        OneToOneSend_Model senddata = oneToOnemodels.get(position);

        holder.Created_On.setText(senddata.getCreatedOn());
        holder.Meeting_On.setText(senddata.getMeetingOn());
        holder.Remark.setText(senddata.getRemark());
        holder.clientName.setText(senddata.getGivenTo().getName());
        holder.serviceProvide.setText(senddata.getGivenTo().getCompany());
        holder.locationName.setText(senddata.getGivenTo().getClub());
        holder.locationDetails.setText(senddata.getGivenTo().getDistName()+", "+senddata.getGivenTo().getCitName());

        Picasso.get().load(senddata.getGivenTo().getImage()).into(holder.profilePicMember, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                //holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                // holder.progressBar.setVisibility(View.GONE);
            }
        });

        String statues = String.valueOf(senddata.getStatus());

        if(statues.equals("null")){

            String[] itemNamesbank = context.getResources().getStringArray(R.array.SendList);
            ArrayAdapter<String> bankaccountadapter = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itemNamesbank);
            bankaccountadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.BusinessStatus.setAdapter(bankaccountadapter);

        }else{

            holder.BusinessStatus.setText(statues);

            String[] itemNamesbank = context.getResources().getStringArray(R.array.SendList);
            ArrayAdapter<String> bankaccountadapter = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itemNamesbank);
            bankaccountadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.BusinessStatus.setAdapter(bankaccountadapter);
        }

        /*holder.BusinessStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/
        holder.BusinessStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // fetch the user selected value
                String item = adapterView.getItemAtPosition(i).toString();

                // create Toast with user selected value
                Toast.makeText(context, item, Toast.LENGTH_LONG).show();




            }
        });

        holder.btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oto_id = senddata.getOtoId();
                String item = holder.BusinessStatus.getText().toString().trim();
                updateStatues(oto_id,item);
                holder.BusinessStatus.setFocusable(false);

            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.BusinessStatus.setFocusable(true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return oneToOnemodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Created_On,Meeting_On,Remark,clientName,serviceProvide,locationName,locationDetails;
        AutoCompleteTextView BusinessStatus;
        Button btn_submit;
        ImageView btn_edit;
        CircleImageView profilePicMember;

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
            profilePicMember = itemView.findViewById(R.id.profilePicMember);
            btn_submit = itemView.findViewById(R.id.btn_submit);
            btn_edit = itemView.findViewById(R.id.btn_edit);

            String[] itemNamesbank = context.getResources().getStringArray(R.array.SendList);
            ArrayAdapter<String> bankaccountadapter = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itemNamesbank);
            bankaccountadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            BusinessStatus.setAdapter(bankaccountadapter);
        }
    }

    public void updateStatues(String user_id,String status){

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Change Status Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("oto_id", user_id);
        params.put("oto_sts", status);

        Call<StatusDetails_Model> call_sataus = simpleApi.update_oto_sts(params);

        call_sataus.enqueue(new Callback<StatusDetails_Model>() {
            @Override
            public void onResponse(Call<StatusDetails_Model> call, Response<StatusDetails_Model> response) {

                progressDialog.dismiss();

                if(response.body() != null){

                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusDetails_Model> call, Throwable t) {

                progressDialog.dismiss();

                Toast.makeText(context, "error"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
