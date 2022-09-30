package com.bceclub.app;

import static com.bceclub.app.MainActivity.member_id;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Adapters.MemberListAdapter;
import com.bceclub.app.Models.MemberShipListModalClass;
import com.bceclub.app.Models.MembersList;
import com.bceclub.app.Models.Membership;
import com.bceclub.app.Models.ProfileModalClass;
import com.bceclub.app.databinding.FragmentBusinessLeadDetailBinding;
import com.bceclub.app.databinding.FragmentMemberFragBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class member_frag extends Fragment implements MemberListAdapter.ViewMemberDetailInterface {

    private FragmentMemberFragBinding binding;
    SimpleApi simpleApi;
    String user_id;
    ArrayList<MemberShipListModalClass> membersArrayList = new ArrayList<>();
    ArrayList<MemberShipListModalClass> localMembersArrayList = new ArrayList<>();
    ArrayList<ProfileModalClass> profile = new ArrayList<>();
    MemberListAdapter globalAdapter;
    MemberListAdapter localAdapter;

    boolean onLocalList = false;

    public member_frag() {
        // Required empty public constructor
    }

    public static member_frag newInstance(String param1, String param2) {
        member_frag fragment = new member_frag();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMemberFragBinding.inflate(inflater, container, false);
        MainActivity activity = (MainActivity) getActivity();
        user_id = activity.getUserId();
        binding.toolbar.setTitle("Members");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getActivity().onBackPressed();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, new home_frag()).commit();

            }
        });

        binding.search.setSubmitButtonEnabled(true);
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                applySearch(newText);
                return false;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //ArrayList<Members> membersArrayList = new ArrayList<Members>();
        localMemberList();
        binding.globalSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getResources().getColor(R.color.red));
                binding.localSearch.setBackgroundColor(getResources().getColor(R.color.darkGreyFont));
                globalMemberList();

            }
        });

        binding.localSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getResources().getColor(R.color.red));
                binding.globalSearch.setBackgroundColor(getResources().getColor(R.color.darkGreyFont));
                localMemberList();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    void localMemberList() {
        onLocalList = true;
        RecyclerView recyclerView = binding.memberListRecyclerView;
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrieved Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        localAdapter = new MemberListAdapter(localMembersArrayList, getContext(), binding.getRoot(), this);

        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        Call<MembersList> call = simpleApi.localmemberlist(params);
        call.enqueue(new Callback<MembersList>() {
            @Override
            public void onResponse(Call<MembersList> call, Response<MembersList> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    for (Membership member : response.body().getMembershipList()) {
                        getProfile(member.getId(), new OnGetProfileListner() {
                            @Override
                            public void onGetProfileSuccess(ProfileModalClass profileModalClass) {
                                MemberShipListModalClass memberShipListModalClass = new MemberShipListModalClass(member, profileModalClass.getImg());
                                localMembersArrayList.add(memberShipListModalClass);
                                localAdapter.updateMemberList(memberShipListModalClass);
                            }

                            @Override
                            public void onGetProfileError(String errorMsg) {
                                Toast.makeText(getContext(),"error getting your profile", Toast.LENGTH_SHORT).show();
                            }
                        });
//                        localMembersArrayList.add(member);
//                        localAdapter.updateMemberList(member);
                    }

                }
            }

            @Override
            public void onFailure(Call<MembersList> call, Throwable t) {
                progressDialog.dismiss();
                call.cancel();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(localAdapter);
    }

    void globalMemberList() {
        onLocalList = false;
        RecyclerView recyclerView = binding.memberListRecyclerView;
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrieved Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        globalAdapter = new MemberListAdapter(membersArrayList, getContext(), binding.getRoot(), this);

        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        Call<MembersList> call = simpleApi.membershipList(params);
        call.enqueue(new Callback<MembersList>() {
            @Override
            public void onResponse(Call<MembersList> call, Response<MembersList> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    for (Membership member : response.body().getMembershipList()) {
                        //Log.d("listsize", String.valueOf(response.body().getMembershipList().size()));
                        getProfile(member.getId(), new OnGetProfileListner() {
                            @Override
                            public void onGetProfileSuccess(ProfileModalClass profileModalClass) {
                                MemberShipListModalClass memberShipListModalClass = new MemberShipListModalClass(member, profileModalClass.getImg());
                                membersArrayList.add(memberShipListModalClass);
                                globalAdapter.updateMemberList(memberShipListModalClass);
                            }

                            @Override
                            public void onGetProfileError(String errorMsg) {

                            }
                        });

//                        membersArrayList.add(member);
//                        globalAdapter.updateMemberList(member);
                    }

                }
            }

            @Override
            public void onFailure(Call<MembersList> call, Throwable t) {
                progressDialog.dismiss();
                call.cancel();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(globalAdapter);
    }

    public void getProfile(String member_id, OnGetProfileListner onGetProfileListner){
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

    void applySearch(String searchString) {
        if (onLocalList) {
            ArrayList<MemberShipListModalClass> searchLocalMembersArrayList = new ArrayList<>();
            searchLocalMembersArrayList.clear();
            for (MemberShipListModalClass membership : localMembersArrayList) {
                if (membership.getMembership().getName().toLowerCase(Locale.ROOT).contains(searchString.toLowerCase(Locale.ROOT))) {
                    searchLocalMembersArrayList.add(membership);
                }
            }
            if (searchLocalMembersArrayList.isEmpty()) {
                // Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show();
                searchLocalMembersArrayList.clear();
            }else {
                localAdapter.updateList(searchLocalMembersArrayList);
            }
        } else {
            ArrayList<MemberShipListModalClass> searchMembersArrayList = new ArrayList<>();
            searchMembersArrayList.clear();
            for (MemberShipListModalClass membership : membersArrayList) {
                if (membership.getMembership().getName().toLowerCase(Locale.ROOT).contains(searchString.toLowerCase(Locale.ROOT))) {
                    searchMembersArrayList.add(membership);
                }
            }
            if (searchMembersArrayList.isEmpty())
                Toast.makeText(requireContext(), "No data Found...", Toast.LENGTH_SHORT).show();
            else
                globalAdapter.updateList(searchMembersArrayList);

        }

    }

    @Override
    public void viewMemberDetails(Membership member) {
        if (onLocalList) {
//            NavDirections action = member_fragDirections.actionMemberFragToMemberDetails(localMembersArrayList.get(position).getId());
//            Navigation.findNavController(binding.getRoot()).navigate(action);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MemberDetails()).commit();
            member_id = member.getId();
        } else {
//            NavDirections action = member_fragDirections.actionMemberFragToMemberDetails(membersArrayList.get(position).getId());
//            Navigation.findNavController(binding.getRoot()).navigate(action);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MemberDetails()).commit();
            member_id = member.getId();
        }

    }

    public interface OnGetProfileListner{
        void onGetProfileSuccess(ProfileModalClass profileModalClass);
        void onGetProfileError(String errorMsg);
    }
}