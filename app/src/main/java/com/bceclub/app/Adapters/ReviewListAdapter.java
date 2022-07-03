package com.bceclub.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.Models.ReviewItem;
import com.bceclub.app.R;

import java.util.ArrayList;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder> {

    ArrayList<ReviewItem> reviewList = new ArrayList<>();

    public ReviewListAdapter(ArrayList<ReviewItem> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.review_item_list, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.date.setText(reviewList.get(position).getDate());
        holder.remark.setText("Remarks: " + reviewList.get(position).getRemark());
        holder.rating.setRating(Float.parseFloat(reviewList.get(position).getRating()));
        holder.name.setText(reviewList.get(position).getReceiveFrom().getName());
        holder.company.setText(reviewList.get(position).getReceiveFrom().getCompany());
        holder.club.setText(reviewList.get(position).getReceiveFrom().getClub() + " - " + reviewList.get(position).getReceiveFrom().getCategory());
    }

    @Override
    public int getItemCount() {

        return reviewList.size();
    }

    public void updateReview(ReviewItem review) {
        //MemberItemList.clear();
        if (!reviewList.contains(review))
            reviewList.add(review);
        notifyDataSetChanged();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView date, remark, name, company, club;
        public RatingBar rating;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.reviewDate);
            this.remark = itemView.findViewById(R.id.reviewRemarks);
            this.name = itemView.findViewById(R.id.reveiveFromName);
            this.company = itemView.findViewById(R.id.receiveFromCompany);
            this.club = itemView.findViewById(R.id.receiveFromClub);
            this.rating = itemView.findViewById(R.id.reviewRating);

        }
    }
}
