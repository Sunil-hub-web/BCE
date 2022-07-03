package com.bceclub.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.Models.FavouriteConnectionModalClass;
import com.bceclub.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavouriteConnectionAdapter extends RecyclerView.Adapter<FavouriteConnectionAdapter.FavouriteListViewHolder> {

    ArrayList<FavouriteConnectionModalClass.RequestConnection> favList = new ArrayList<>();

    public FavouriteConnectionAdapter(ArrayList<FavouriteConnectionModalClass.RequestConnection> favList) {
        this.favList = favList;
    }


    @NonNull
    @Override
    public FavouriteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.favourite_connection_item, parent, false);
        FavouriteListViewHolder viewHolder = new FavouriteListViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteListViewHolder holder, int position) {

        holder.memberName.setText(favList.get(position).getName().toString());
        holder.memberClub.setText(favList.get(position).getClubName());
        holder.memberDesig.setText(favList.get(position).getCategory());


        //Picasso.get().load(MemberItemList.get(position).getProfilePic()).into(holder.profilePic);
        if (!favList.get(position).getImage().isEmpty())
            Picasso.get().load(favList.get(position).getImage()).into(holder.profilePic, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    holder.progressBar.setVisibility(View.GONE);
                }
            });
        else
            Picasso.get().load("https://www.freeiconspng.com/uploads/customers-icon-3.png").into(holder.profilePic, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    holder.progressBar.setVisibility(View.GONE);

                }
            });


    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public void updateList(FavouriteConnectionModalClass.RequestConnection connection) {
        //MemberItemList.clear();
        if (!favList.contains(connection))
            favList.add(connection);
        notifyDataSetChanged();
    }

    public static class FavouriteListViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView profilePic;
        public TextView memberName, memberClub, memberDesig;
        ProgressBar progressBar;

        public FavouriteListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.profilePic = itemView.findViewById(R.id.profilePicMember);
            this.memberName = (TextView) itemView.findViewById(R.id.memberName);
            this.memberClub = itemView.findViewById(R.id.memberClub);
            this.memberDesig = itemView.findViewById(R.id.memberDesig);
            this.progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

}
