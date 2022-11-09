package com.bceclub.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Adapters.OneToOneReceiveAdapter;
import com.bceclub.app.Adapters.OneToOneSendAdapter;
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
    OneToOneSendAdapter oneToOneAdapter;
    OneToOneReceiveAdapter oneToOneAdapter1;
    ArrayList<OneToOneSend_Model> oneToOneSend_models = new ArrayList<>();
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

        binding.toolbar.setTitle("One To One");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().onBackPressed();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new home_frag()).commit();
            }
        });

        binding.btnSendonetonne.setOnClickListener(view1 ->
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new member_frag()).addToBackStack(null).commit());

        MainActivity activity = (MainActivity) getActivity();
        user_id = activity.getUserId();

        binding.ReceiveList.setOnClickListener(view1 -> {

            view1.setBackgroundColor(getResources().getColor(R.color.red));
            binding.SendList.setBackgroundColor(getResources().getColor(R.color.darkGreyFont));
            onetooneReceiveList();
        });
        binding.SendList.setOnClickListener(view1 -> {
            view1.setBackgroundColor(getResources().getColor(R.color.red));
            binding.ReceiveList.setBackgroundColor(getResources().getColor(R.color.darkGreyFont));
            onetooneSendList();
        });

        onetooneReceiveList();

        return view;
    }

    public void onetooneSendList(){

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

                progressDialog.dismiss();

                oneToOneSend_models.clear();

                if(response.isSuccessful()){

                    if(response.body()!= null){

                        for(OneToOneSend_Model send : response.body().getSendList()){

                            oneToOneSend_models.add(send);
                            LinearLayoutManager linearLayoutManager =
                                    new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                            oneToOneAdapter = new OneToOneSendAdapter(getContext(),oneToOneSend_models);
                            binding.recycleronetoone.setLayoutManager(linearLayoutManager);
                            binding.recycleronetoone.setHasFixedSize(true);
                            binding.recycleronetoone.setAdapter(oneToOneAdapter);

                        }

                    }else{

                        Toast.makeText(getContext(), "Data Not Avilable", Toast.LENGTH_SHORT).show();

                    }
                }else{

                    Toast.makeText(getContext(), "Data Not Avilable", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<onetonedet_model> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getContext(), "error"+t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onetooneReceiveList(){

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

                progressDialog.dismiss();

                oneToOneReceive_models.clear();

                if(response.isSuccessful()){

                    if(response.body()!= null){

                        for(OneToOneReceive_Model receive : response.body().getReceiveList()){

                            oneToOneReceive_models.add(receive);
                            LinearLayoutManager linearLayoutManager =
                                    new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                            oneToOneAdapter1 = new OneToOneReceiveAdapter(getContext(),oneToOneReceive_models);
                            binding.recycleronetoone.setLayoutManager(linearLayoutManager);
                            binding.recycleronetoone.setHasFixedSize(true);
                            binding.recycleronetoone.setAdapter(oneToOneAdapter1);

                        }

                    }else{

                        Toast.makeText(getContext(), "Data Not Avilable", Toast.LENGTH_SHORT).show();

                    }
                }else{

                    Toast.makeText(getContext(), "Data Not Avilable", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<onetonedet_model> call, Throwable t) {

                progressDialog.dismiss();

                Toast.makeText(getContext(), "error"+t, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
