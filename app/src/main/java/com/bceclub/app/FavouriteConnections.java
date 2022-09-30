package com.bceclub.app;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Adapters.FavouriteConnectionAdapter;
import com.bceclub.app.Models.FavouriteConnectionModalClass;
import com.bceclub.app.databinding.FragmentFavouriteConnectionsBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteConnections extends Fragment {

    FragmentFavouriteConnectionsBinding binding;
    FavouriteConnectionAdapter mAdapter;
    ArrayList<FavouriteConnectionModalClass.RequestConnection> favList = new ArrayList<>();
    String user_id;


    public FavouriteConnections() {
        // Required empty public constructor
    }
    public static FavouriteConnections newInstance(String param1, String param2) {
        FavouriteConnections fragment = new FavouriteConnections();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerView = binding.memberListRecyclerView;
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrieved Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        mAdapter = new FavouriteConnectionAdapter(favList);
        SimpleApi simpleApi;
        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        Call<FavouriteConnectionModalClass> call = simpleApi.favouriteconnection(params);
        call.enqueue(new Callback<FavouriteConnectionModalClass>() {
            @Override
            public void onResponse(Call<FavouriteConnectionModalClass> call, Response<FavouriteConnectionModalClass> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    favList.clear();
                    if(response.body().getRequestConnection() != null) {

                        for (FavouriteConnectionModalClass.RequestConnection requestConnection : response.body().getRequestConnection()) {
                            favList.add(requestConnection);
                            mAdapter.updateList(requestConnection);
                        }
                    }
                    else {

                            binding.noconnections.setVisibility(View.VISIBLE);
                            binding.memberListRecyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<FavouriteConnectionModalClass> call, Throwable t) {
                progressDialog.dismiss();
                call.cancel();
                throw new RuntimeException("Test Crash for BceClube");
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(mAdapter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity activity = (MainActivity) getActivity();
        user_id = activity.getUserId();
        binding = FragmentFavouriteConnectionsBinding.inflate(inflater, container, false);
        binding.toolbar.setTitle("Favourite Connections");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().onBackPressed();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,new home_frag()).commit();
            }
        });
        return binding.getRoot();
    }
}