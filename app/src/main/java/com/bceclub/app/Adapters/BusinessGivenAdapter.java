package com.bceclub.app.Adapters;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.Models.BusinessGivenModalClass;
import com.bceclub.app.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class BusinessGivenAdapter extends RecyclerView.Adapter<BusinessGivenAdapter.BusinessGivenViewholder> {

    ArrayList<BusinessGivenModalClass.Receive> businessGivenList = new ArrayList<>();
    Context con;
    Boolean send;

    public BusinessGivenAdapter(ArrayList<BusinessGivenModalClass.Receive> businessGivenList, Context con, Boolean send) {
        this.businessGivenList = businessGivenList;
        this.con = con;
        this.send = send;
    }

    @NonNull
    @Override
    public BusinessGivenViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.business_send_item, parent, false);
        BusinessGivenViewholder viewHolder = new BusinessGivenViewholder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessGivenViewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.date.setText(businessGivenList.get(position).getDate());
        holder.remark.setText("Remarks : "+businessGivenList.get(position).getSlipDetails().getRemark());
        holder.amt.setText("Amount : "+businessGivenList.get(position).getSlipDetails().getAmount());
        holder.memberName.setText(businessGivenList.get(position).getReceiveFrom().getName());
        holder.memberClub.setText(businessGivenList.get(position).getReceiveFrom().getClubName());
        holder.memberDesig.setText(businessGivenList.get(position).getReceiveFrom().getCategory());
        if(send){
            holder.label.setText("Send to");
        }
        else {
            holder.label.setText("Received From");
        }
//        holder.invoice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                downloadFile(businessGivenList.get(position).getSlipDetails().getInvoice());
//            }
//        });

        if (!businessGivenList.get(position).getReceiveFrom().getImage().isEmpty())
            Picasso.get().load(businessGivenList.get(position).getReceiveFrom().getImage()).into(holder.profilePic);
        else
            Picasso.get().load("https://www.freeiconspng.com/uploads/customers-icon-3.png").into(holder.profilePic);
    }

    @Override
    public int getItemCount() {
        return businessGivenList.size();
    }

    public void updateBusinessGiven(BusinessGivenModalClass.Receive item){
        if (!businessGivenList.contains(item))
            businessGivenList.add(item);
        notifyDataSetChanged();
    }

    public void downloadFile(String DownloadUrl) {

        File direct = new File(con.getExternalFilesDir(null), "/Invoice");
        if(!direct.exists()){
            direct.mkdir();
        }

        DownloadManager.Request request1 = new DownloadManager.Request(Uri.parse(DownloadUrl));
        request1.setDescription("Invoice Downloading..");   //appears the same in Notification bar while downloading
        request1.setTitle("Invoice");
        request1.setVisibleInDownloadsUi(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request1.allowScanningByMediaScanner();
            request1.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        }
        request1.setDestinationInExternalFilesDir(con, "/File", "/Invoice");

        DownloadManager manager1 = (DownloadManager) con.getSystemService(Context.DOWNLOAD_SERVICE);
        Objects.requireNonNull(manager1).enqueue(request1);
        if (DownloadManager.STATUS_SUCCESSFUL == 8) {
            Toast.makeText(con, "Downloaded successfully...", Toast.LENGTH_SHORT).show();
        }
    }


    class BusinessGivenViewholder extends RecyclerView.ViewHolder{
        TextView date, remark, amt, label, invoice;
        TextView memberName, memberClub, memberDesig;
        CircleImageView profilePic;
        public BusinessGivenViewholder(@NonNull View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.date);
            this.remark = itemView.findViewById(R.id.remark);
            this.memberName = itemView.findViewById(R.id.memberName);
            this.memberClub = itemView.findViewById(R.id.memberClub);
            this.memberDesig = itemView.findViewById(R.id.memberDesig);
            this.profilePic = itemView.findViewById(R.id.profilePicMember);
            this.amt = itemView.findViewById(R.id.Amount);
            this.label = itemView.findViewById(R.id.BusinessLabel);
            this.invoice = itemView.findViewById(R.id.invoiceDownload);

        }
    }
}
