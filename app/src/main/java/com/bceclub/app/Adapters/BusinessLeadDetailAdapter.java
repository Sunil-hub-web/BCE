package com.bceclub.app.Adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Models.BusinessLeadDetailModalClass;
import com.bceclub.app.Models.DialogBoxModalClass;
import com.bceclub.app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessLeadDetailAdapter extends RecyclerView.Adapter<BusinessLeadDetailAdapter.BusinessLeadDetailViewHolder> {

    ArrayList<BusinessLeadDetailModalClass.Receive> businessLead = new ArrayList<>();
    Context context;
    private static final int REQUEST_PHONE_CALL = 1001;

    public BusinessLeadDetailAdapter(FragmentActivity activity, ArrayList<BusinessLeadDetailModalClass.Receive> businessLead) {
        this.businessLead = businessLead;
        this.context = activity;
    }


    @NonNull
    @Override
    public BusinessLeadDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.business_lead_item, parent, false);
        BusinessLeadDetailViewHolder viewHolder = new BusinessLeadDetailViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessLeadDetailViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String[] type = new String[]{"Not connected", "Connected business not done", "Business Closed"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.dropdown_menu_popup_item, type);
        holder.status.setAdapter(adapter);

        holder.status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (type[i] == ("Business Closed")) {
                    holder.btn_uploadInvoice.setVisibility(View.VISIBLE);
                    holder.amount.setVisibility(View.VISIBLE);
                } else {
                    holder.btn_uploadInvoice.setVisibility(View.GONE);
                    holder.amount.setVisibility(View.GONE);
                }
            }
        });

        holder.date.setText(businessLead.get(position).getDateInfo().getDate());
        holder.stars.setText(businessLead.get(position).getDateInfo().getRating());
        holder.title.setText(businessLead.get(position).getDateInfo().getTitle());

        holder.name.setText(businessLead.get(position).getBusinessLeadDetails().getName());
        holder.phone.setText(businessLead.get(position).getBusinessLeadDetails().getMobile());
        holder.remark.setText(businessLead.get(position).getBusinessLeadDetails().getRemark());

        //holder.status.setText(businessLead.get(position).getLeadStatus().getStatus());

        if (businessLead.get(position).getLeadStatus().getStatus() == null) {
            businessLead.get(position).getLeadStatus().setStatus("");
        }

        switch (businessLead.get(position).getLeadStatus().getStatus()) {
            case "":
                holder.statusText.setVisibility(View.GONE);
                holder.amt.setVisibility(View.GONE);
                holder.layout.setVisibility(View.VISIBLE);
                break;
            case "Business Closed":
                holder.statusText.setVisibility(View.VISIBLE);
                holder.layout.setVisibility(View.GONE);
                holder.submit.setVisibility(View.GONE);
                holder.status.setText(businessLead.get(position).getLeadStatus().getStatus());
                holder.amt.setText("Amount: " + businessLead.get(position).getLeadStatus().getAmount());
                break;
            default:
                holder.statusText.setVisibility(View.VISIBLE);
                holder.layout.setVisibility(View.GONE);
                holder.submit.setVisibility(View.GONE);
                holder.amt.setVisibility(View.GONE);
                holder.status.setText(businessLead.get(position).getLeadStatus().getStatus());

        }

        holder.statusText.setText(businessLead.get(position).getLeadStatus().getStatus());


        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.status.length() == 0) {
                    holder.status.setError("Please select status !");
                } else {
                    if (holder.status.getText().toString().equals("Business Closed")) {
                        if (!(holder.amount.length() == 0)) {
                            submitStatus(businessLead.get(position).getRef_id(), holder.amount.getText().toString(), holder.status.getText().toString(), view);
                        } else
                            holder.amt.setError("This field is required !");
                    } else {
                        submitStatus(businessLead.get(position).getRef_id(), "", holder.status.getText().toString(), view);
                    }
                }


            }
        });

        holder.btn_uploadInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        holder.imageCalling.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    String number = businessLead.get(position).getBusinessLeadDetails().getMobile();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + number));
                    context.startActivity(callIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return businessLead.size();
    }

    public void updateBusinessLead(BusinessLeadDetailModalClass.Receive item) {
        if (!businessLead.contains(item))
            businessLead.add(item);
        notifyDataSetChanged();
    }

    void submitStatus(String ref_id, String ref_wrk_amount, String ref_work_sts, View view) {
        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("ref_id", ref_id);
        params.put("ref_wrk_amount", ref_wrk_amount);
        params.put("ref_work_sts", ref_work_sts);
        Call<DialogBoxModalClass> call = simpleApi.leadstatusupdate(params);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        call.enqueue(new Callback<DialogBoxModalClass>() {
            @Override
            public void onResponse(Call<DialogBoxModalClass> call, Response<DialogBoxModalClass> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DialogBoxModalClass> call, Throwable t) {
                progressDialog.dismiss();
                view.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }

    static class BusinessLeadDetailViewHolder extends RecyclerView.ViewHolder {

        TextView date, title, name, stars, phone, remark, amt, statusText;
        AutoCompleteTextView status;
        ImageView imageCalling;
        TextInputEditText amount;
        Button submit,btn_uploadInvoice;
        TextInputLayout layout;

        public BusinessLeadDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.date);
            this.title = itemView.findViewById(R.id.title);
            this.name = itemView.findViewById(R.id.profileName);
            this.stars = itemView.findViewById(R.id.rating);
            this.phone = itemView.findViewById(R.id.phone);
            this.remark = itemView.findViewById(R.id.remark);
            this.amt = itemView.findViewById(R.id.amount);
            this.status = itemView.findViewById(R.id.BusinessStatus);
            this.statusText = itemView.findViewById(R.id.status);
            this.imageCalling = itemView.findViewById(R.id.imageCalling);
            this.amount = itemView.findViewById(R.id.amountInp);
            this.btn_uploadInvoice = itemView.findViewById(R.id.btn_uploadInvoice);
            this.submit = itemView.findViewById(R.id.submitStatus);
            this.layout = itemView.findViewById(R.id.spinnerLayout);
        }
    }


}