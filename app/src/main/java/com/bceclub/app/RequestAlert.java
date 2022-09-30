package com.bceclub.app;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Adapters.RequestReceivedAdapter;
import com.bceclub.app.Adapters.RequestSentAdapter;
import com.bceclub.app.Models.ConnectionListModalClass;
import com.bceclub.app.Models.DialogBoxModalClass;
import com.bceclub.app.databinding.FragmentRequestAlertBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAlert extends Fragment implements RequestReceivedAdapter.AcceptRequestInterface {

    FragmentRequestAlertBinding binding;
    String user_id;
    RequestReceivedAdapter requestReceivedAdapter;
    RequestSentAdapter requestSentAdapter;
    ArrayList<ConnectionListModalClass.ResuestReceived> resuestReceiveds = new ArrayList<>();
    ArrayList<ConnectionListModalClass.ResuestSend> requestSends = new ArrayList<>();

    public RequestAlert() {
        // Required empty public constructor
    }

    public static RequestAlert newInstance(String param1, String param2) {
        RequestAlert fragment = new RequestAlert();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        receiveRequests();
        
        binding.requestReceiveRecyclerView.setVisibility(View.VISIBLE);
        binding.sendReceiveRecyclerView.setVisibility(View.GONE);

        binding.btnReceiveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setBackgroundColor(getResources().getColor(R.color.red));
                binding.btnSendList.setBackgroundColor(getResources().getColor(R.color.darkGreyFont));
                binding.requestReceiveRecyclerView.setVisibility(View.VISIBLE);
                binding.sendReceiveRecyclerView.setVisibility(View.GONE);
                receiveRequests();
            }
        });

        binding.btnSendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getResources().getColor(R.color.red));
                binding.btnReceiveList.setBackgroundColor(getResources().getColor(R.color.darkGreyFont));
                binding.requestReceiveRecyclerView.setVisibility(View.GONE);
                binding.sendReceiveRecyclerView.setVisibility(View.VISIBLE);
                sendRequest();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    void receiveRequests() {

        requestReceivedAdapter = new RequestReceivedAdapter(resuestReceiveds, getContext(), this);
        RecyclerView recyclerView = binding.requestReceiveRecyclerView;
        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        Call<ConnectionListModalClass> call = simpleApi.connectionlist(params);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrieved Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        call.enqueue(new Callback<ConnectionListModalClass>() {
            @Override
            public void onResponse(Call<ConnectionListModalClass> call, Response<ConnectionListModalClass> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    resuestReceiveds.clear();

                    Log.d("dataresponse",response.body().getResuestReceived().toString());

                    if(response.body().getResuestReceived() !=null){

                        for (ConnectionListModalClass.ResuestReceived resuestReceived : response.body().getResuestReceived()) {
                            if (!resuestReceiveds.contains(resuestReceived))
                                resuestReceiveds.add(resuestReceived);
                            recyclerView.setVisibility(View.VISIBLE);
                            requestReceivedAdapter.updateList(resuestReceived);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            recyclerView.setAdapter(requestReceivedAdapter);
                        }
                    }else{
                        Toast.makeText(getActivity(), "No Data Is Available", Toast.LENGTH_SHORT).show();

                        recyclerView.setVisibility(View.GONE);
                    }
                    
                }
            }

            @Override
            public void onFailure(Call<ConnectionListModalClass> call, Throwable t) {
                progressDialog.dismiss();
                call.cancel();
                throw new RuntimeException("Test Crash for BceClube"); // Force a crash
            }
        });

    }

    void sendRequest() {

        requestSentAdapter = new RequestSentAdapter(requestSends);
        RecyclerView recyclerView = binding.sendReceiveRecyclerView;
        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        Call<ConnectionListModalClass> call = simpleApi.connectionlist(params);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrieved Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        call.enqueue(new Callback<ConnectionListModalClass>() {
            @Override
            public void onResponse(Call<ConnectionListModalClass> call, Response<ConnectionListModalClass> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    requestSends.clear();

                    if (response.body().getResuestSend() != null) {

                        for (ConnectionListModalClass.ResuestSend requestSent : response.body().getResuestSend()) {

                            recyclerView.setVisibility(View.VISIBLE);
                            
                            if (!requestSends.contains(requestSent))
                                requestSends.add(requestSent);
                            requestSentAdapter.updateList(requestSent);

                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            recyclerView.setAdapter(requestSentAdapter);
                        }
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "No Data Is Available", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ConnectionListModalClass> call, Throwable t) {
                progressDialog.dismiss();
                call.cancel();
                throw new RuntimeException("Test Crash for BceClube"); // Force a crash
            }
        });

    }


    void accptRequest(String user_id, String request_id) {

        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("request_id", request_id);
        Call<DialogBoxModalClass> call = simpleApi.acceptRequest(params);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Accepting Request Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        call.enqueue(new Callback<DialogBoxModalClass>() {
            @Override
            public void onResponse(Call<DialogBoxModalClass> call, Response<DialogBoxModalClass> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DialogBoxModalClass> call, Throwable t) {
                progressDialog.dismiss();
                call.cancel();
                throw new RuntimeException("Test Crash for BceClube"); // Force a crash
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRequestAlertBinding.inflate(inflater, container, false);
        binding.toolbar.setTitle("Request Alert");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        MainActivity activity = (MainActivity) getActivity();
        user_id = activity.getUserId();
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getActivity().onBackPressed();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, new home_frag()).commit();
            }
        });
        return binding.getRoot();
    }


    @Override
    public void acceptRequest(ConnectionListModalClass.ResuestReceived resuestReceived) {
        accptRequest(user_id, resuestReceived.getRequestId());
    }
}