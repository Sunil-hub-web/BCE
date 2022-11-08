package com.bceclub.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Adapters.OneToOneAdapter;
import com.bceclub.app.Models.OneToOneReceive_Model;
import com.bceclub.app.Models.OneToOneSend_Model;
import com.bceclub.app.Models.onetonedet_model;
import com.bceclub.app.databinding.OnetoonefragBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneToOne_frg extends Fragment {

    OnetoonefragBinding binding;
    OneToOneAdapter oneToOneAdapter;
    ArrayList<OneToOneSend_Model> oneToOne_models = new ArrayList<>();
    ArrayList<OneToOneReceive_Model> oneToOneReceive_models = new ArrayList<>();
    SimpleApi simpleApi;
    String user_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = OnetoonefragBinding.inflate(getLayoutInflater(),container,false);
        View view = binding.getRoot();

        binding.btnSendonetonne.setOnClickListener(view1 ->
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new member_frag()).addToBackStack(null).commit());



        return view;
    }

    public void onetoonedata(){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Retrive Guest List Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);

        Call<onetonedet_model> call_onetoone = simpleApi.onetoonelist(params);


        call_onetoone.enqueue(new Callback<onetonedet_model>() {
            @Override
            public void onResponse(Call<onetonedet_model> call, Response<onetonedet_model> response) {

                if(response.isSuccessful()){

                    if(response.body()!= null){

                        for(OneToOneSend_Model send : response.body().getSendList()){

                        }

                    }else{


                    }
                }

            }

            @Override
            public void onFailure(Call<onetonedet_model> call, Throwable t) {

            }
        });

    }
}
