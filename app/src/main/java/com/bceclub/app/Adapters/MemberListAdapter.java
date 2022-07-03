package com.bceclub.app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Models.MemberShipListModalClass;
import com.bceclub.app.Models.Membership;
import com.bceclub.app.Models.ProfileModalClass;
import com.bceclub.app.R;
import com.bceclub.app.Utils.ApiCalls;
import com.bceclub.app.member_frag;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder> implements ApiCalls.APIResult {

    private ArrayList<MemberShipListModalClass> MemberItemList = new ArrayList<MemberShipListModalClass>();
    private ViewMemberDetailInterface viewMemberDetailInterface;

    private String profile = "";
    private Context con;
    private View fragmentView;

    public MemberListAdapter(ArrayList<MemberShipListModalClass> memberItemList, Context con, View fragmentView, ViewMemberDetailInterface viewMemberDetailInterface) {
        this.MemberItemList = memberItemList;
        this.con = con;
        this.fragmentView = fragmentView;
        this.viewMemberDetailInterface = viewMemberDetailInterface;
    }

    public void updateList(ArrayList<MemberShipListModalClass> newList) {
//        MemberItemList.clear();
//        MemberItemList.addAll(newList);
        MemberItemList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MemberListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.member_item, parent, false);
        MemberListViewHolder viewHolder = new MemberListViewHolder(listItem, viewMemberDetailInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberListViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.memberClub.setText(MemberItemList.get(position).getMembership().getClubName());
        holder.memberDesig.setText(MemberItemList.get(position).getMembership().getCategory() +
                "-" + MemberItemList.get(position).getMembership().getSubCategory());

       /* String name  = MemberItemList.get(position).getMembership().getName().toString();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        holder.memberName.setText(name);*/

        // create a string
        String message = MemberItemList.get(position).getMembership().getName().toString();

        // stores each characters to a char array
        char[] charArray = message.toCharArray();
        boolean foundSpace = true;

        for(int i = 0; i < charArray.length; i++) {

            // if the array element is a letter
            if(Character.isLetter(charArray[i])) {

                // check space is present before the letter
                if(foundSpace) {

                    // change the letter into uppercase
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            }

            else {
                // if the new character is not character
                foundSpace = true;
            }
        }

        // convert the char array to the string
        message = String.valueOf(charArray);
        System.out.println("Message: " + message);
        holder.memberName.setText(message);

        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMemberDetailInterface.viewMemberDetails(MemberItemList.get(position).getMembership());
            }
        });

        holder.view_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewMemberDetailInterface.viewMemberDetails(MemberItemList.get(position).getMembership());
            }
        });

        //Picasso.get().load(MemberItemList.get(position).getProfilePic()).into(holder.profilePic);
        if (!MemberItemList.get(position).getProfilePic().isEmpty())
            Picasso.get().load(MemberItemList.get(position).getProfilePic()).into(holder.profilePic, new com.squareup.picasso.Callback() {
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


//        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(fragmentView).navigate(R.id.action_member_frag_to_memberDetails);
//            }
//        });

//        ApiCalls apiCalls = new ApiCalls();
//        ProfileModalClass profileModalClass = apiCalls.getProfile(MemberItemList.get(position).getId(), this);
        //Picasso.get().load(profile).into(holder.profilePic);


//
//        Log.d("VAMSEE KRISHANANAAANANA", "onBindViewHolder: "+profile.toString());

    }


    @Override
    public int getItemCount() {
        return MemberItemList.size();
    }

    public void updateMemberList(MemberShipListModalClass members) {
        //MemberItemList.clear();
        if (!MemberItemList.contains(members))
            MemberItemList.add(members);
        notifyDataSetChanged();
    }


    @Override
    public void success(ProfileModalClass profileModalClass) {
        this.profile = profileModalClass.getImg();
    }

    @Override
    public void error(Throwable t) {

    }

    public static class MemberListViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView profilePic;
        public TextView memberName, memberClub, memberDesig;
        public TextView viewDetails;
        ViewMemberDetailInterface viewMemberDetailInterface;
        ProgressBar progressBar;
        ConstraintLayout view_Details;

        public MemberListViewHolder(@NonNull View itemView, ViewMemberDetailInterface viewMemberDetailInterface) {
            super(itemView);
            this.profilePic = itemView.findViewById(R.id.profilePicMember);
            this.memberName = (TextView) itemView.findViewById(R.id.memberName);
            this.memberClub = itemView.findViewById(R.id.memberClub);
            this.memberDesig = itemView.findViewById(R.id.memberDesig);
            this.viewDetails = itemView.findViewById(R.id.viewMemberDetails);
            this.progressBar = itemView.findViewById(R.id.progressBar);
            this.view_Details = itemView.findViewById(R.id.view_Details);
            this.viewMemberDetailInterface = viewMemberDetailInterface;


        }


    }

    public void getProfile(String member_id, member_frag.OnGetProfileListner onGetProfileListner) {
        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", member_id);
        Call<ProfileModalClass> call = simpleApi.getProfile(params);
        call.enqueue(new Callback<ProfileModalClass>() {
            @Override
            public void onResponse(Call<ProfileModalClass> call, Response<ProfileModalClass> response) {
                if (response.isSuccessful()) {
                    response.body().getImg();
                    onGetProfileListner.onGetProfileSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProfileModalClass> call, Throwable t) {
                call.cancel();
                onGetProfileListner.onGetProfileError(t.getMessage());
            }
        });
    }

    public interface ViewMemberDetailInterface {
        void viewMemberDetails(Membership member);
    }


}



