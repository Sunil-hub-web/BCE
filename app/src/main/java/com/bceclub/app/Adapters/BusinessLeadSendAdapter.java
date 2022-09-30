package com.bceclub.app.Adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.Models.BusinessLeadDetailModalClass;
import com.bceclub.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BusinessLeadSendAdapter extends RecyclerView.Adapter<BusinessLeadSendAdapter.BusinessLeadDetailSendViewHolder> {

    ArrayList<BusinessLeadDetailModalClass.Send> businessLeadSend = new ArrayList<>();
    Context context;
    private static final int REQUEST_PHONE_CALL = 1001;

    public BusinessLeadSendAdapter(FragmentActivity activity, ArrayList<BusinessLeadDetailModalClass.Send> businessLeadSend) {
        this.businessLeadSend = businessLeadSend;
        this.context = activity;
    }

    public void updateBusinessLead(BusinessLeadDetailModalClass.Send item){
        if (!businessLeadSend.contains(item))
            businessLeadSend.add(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BusinessLeadDetailSendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.business_lead_send_item, parent, false);
        BusinessLeadDetailSendViewHolder viewHolder = new BusinessLeadDetailSendViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessLeadDetailSendViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.date.setText(businessLeadSend.get(position).getDateInfo().getDate());
        holder.stars.setText(businessLeadSend.get(position).getDateInfo().getRating());
        holder.title.setText(businessLeadSend.get(position).getDateInfo().getTitle());

        holder.name.setText(businessLeadSend.get(position).getBusinessLeadDetails().getName());
        holder.phone.setText(businessLeadSend.get(position).getBusinessLeadDetails().getMobile());
        holder.remark.setText(businessLeadSend.get(position).getBusinessLeadDetails().getRemark());

        holder.memberName.setText(businessLeadSend.get(position).getBusinessLeadGivenTo().getName());
        holder.memberClub.setText(businessLeadSend.get(position).getBusinessLeadGivenTo().getClubName());
        holder.memberDesig.setText(businessLeadSend.get(position).getBusinessLeadGivenTo().getCategory());

        if (!businessLeadSend.get(position).getBusinessLeadGivenTo().getImage().isEmpty())
            Picasso.get().load(businessLeadSend.get(position).getBusinessLeadGivenTo().getImage()).into(holder.profilePic);
        else
            Picasso.get().load("https://www.freeiconspng.com/uploads/customers-icon-3.png").into(holder.profilePic);

        holder.imageCalling.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    String number = businessLeadSend.get(position).getBusinessLeadDetails().getMobile();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+number));
                    context.startActivity(callIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return businessLeadSend.size();
    }

    static class BusinessLeadDetailSendViewHolder extends RecyclerView.ViewHolder{

        TextView date, title, name, stars, phone, remark, amt;
        TextView memberName, memberClub, memberDesig;
        CircleImageView profilePic;
        ImageView imageCalling;
        public BusinessLeadDetailSendViewHolder(@NonNull View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.date);
            this.title = itemView.findViewById(R.id.title);
            this.name = itemView.findViewById(R.id.profileName);
            this.stars = itemView.findViewById(R.id.rating);
            this.phone = itemView.findViewById(R.id.phone);
            this.remark = itemView.findViewById(R.id.remark);
            this.memberName = itemView.findViewById(R.id.memberName);
            this.memberClub = itemView.findViewById(R.id.memberClub);
            this.memberDesig = itemView.findViewById(R.id.memberDesig);
            this.profilePic = itemView.findViewById(R.id.profilePicMember);
            this.imageCalling = itemView.findViewById(R.id.imageCalling);
        }
    }
}
