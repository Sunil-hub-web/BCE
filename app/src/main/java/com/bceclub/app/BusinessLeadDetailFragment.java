package com.bceclub.app;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.Toast;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Adapters.BusinessLeadDetailAdapter;
import com.bceclub.app.Adapters.BusinessLeadSendAdapter;
import com.bceclub.app.Models.BusinessLeadDetailModalClass;
import com.bceclub.app.databinding.FragmentBusinessLeadDetailBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BusinessLeadDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusinessLeadDetailFragment extends Fragment {

    FragmentBusinessLeadDetailBinding binding;
    BusinessLeadDetailAdapter mAdapter;
    BusinessLeadSendAdapter sendAdapter;
    ArrayList<BusinessLeadDetailModalClass.Receive> businessLeadReceive = new ArrayList<>();
    ArrayList<BusinessLeadDetailModalClass.Send> businessLeadSend = new ArrayList<>();
    SimpleApi simpleApi;
    String user_id;

    public BusinessLeadDetailFragment() {
        // Required empty public constructor
    }

    public static BusinessLeadDetailFragment newInstance(String param1, String param2) {
        BusinessLeadDetailFragment fragment = new BusinessLeadDetailFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        sendAdapter = new BusinessLeadSendAdapter(getActivity(), businessLeadSend);
        mAdapter = new BusinessLeadDetailAdapter(getActivity(), businessLeadReceive);
        RecyclerView recyclerView = binding.businessLeadDetailRecyclerView;
        RecyclerView recyclerView2 = binding.businessLeadSend;

        Button btn_SendList = binding.btnSendList;
        Button btn_ReceiveList = binding.btnReceiveList;

        //businessLeadSendList();

        businessLeadReceive();

        btn_SendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Data Retrived Please Wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                recyclerView.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.VISIBLE);

                btn_ReceiveList.setBackgroundColor(btn_ReceiveList.getContext().getResources().getColor(R.color.lightGreyFont));
                btn_SendList.setBackgroundColor(btn_SendList.getContext().getResources().getColor(R.color.red));

                simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);

                Call<BusinessLeadDetailModalClass> call = simpleApi.businessRecieve(params);

                call.enqueue(new Callback<BusinessLeadDetailModalClass>() {
                    @Override
                    public void onResponse(Call<BusinessLeadDetailModalClass> call, Response<BusinessLeadDetailModalClass> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();
                            businessLeadSend.clear();

                            if(response.body().getSendList() != null){

                                for(BusinessLeadDetailModalClass.Send businessReceiveList : response.body().getSendList()){

                                    businessLeadSend.add(businessReceiveList);
                                    sendAdapter.updateBusinessLead(businessReceiveList);
                                }
                            }else{

                                Toast.makeText(getActivity(), "Data Is Not Avilable", Toast.LENGTH_SHORT).show();
                            }
                            
                        }else{

                            Toast.makeText(getActivity(), "Data Is Not Avilable", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BusinessLeadDetailModalClass> call, Throwable t) {
                        call.cancel();

                        progressDialog.dismiss();
                        throw new RuntimeException("Test Crash for BceClube");
                    }
                });

                recyclerView2.setHasFixedSize(true);
                recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView2.setAdapter(sendAdapter);

            }
        });

        btn_ReceiveList.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Data Retrived Please Wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                recyclerView2.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                btn_ReceiveList.setBackgroundColor(btn_ReceiveList.getContext().getResources().getColor(R.color.red));
                btn_SendList.setBackgroundColor(btn_SendList.getContext().getResources().getColor(R.color.lightGreyFont));

                simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);

                Call<BusinessLeadDetailModalClass> call = simpleApi.businessRecieve(params);

                call.enqueue(new Callback<BusinessLeadDetailModalClass>() {
                    @Override
                    public void onResponse(Call<BusinessLeadDetailModalClass> call, Response<BusinessLeadDetailModalClass> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();
                            businessLeadReceive.clear();
                            for(BusinessLeadDetailModalClass.Receive businessReceiveList : response.body().getReceiveList()){

                                businessLeadReceive.add(businessReceiveList);
                                mAdapter.updateBusinessLead(businessReceiveList);
                            }
                        }else{

                            Toast.makeText(getActivity(), "Data Is Not Avilable", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BusinessLeadDetailModalClass> call, Throwable t) {
                        call.cancel();

                        progressDialog.dismiss();
                        throw new RuntimeException("Test Crash for BceClube");
                    }
                });

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(mAdapter);

            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity activity = (MainActivity) getActivity();
        user_id = activity.getUserId();
        binding = FragmentBusinessLeadDetailBinding.inflate(inflater, container, false);
        binding.toolbar.setTitle("Business Lead Given");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getActivity().onBackPressed();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,new home_frag()).commit();
            }
        });
        return binding.getRoot();
    }

    void businessLeadSendList(){
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrived Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        binding.businessLeadDetailRecyclerView.setVisibility(View.GONE);
        binding.businessLeadSend.setVisibility(View.VISIBLE);

        binding.btnReceiveList.setBackgroundColor(binding.btnReceiveList.getContext().getResources().getColor(R.color.lightGreyFont));
        binding.btnSendList.setBackgroundColor(binding.btnSendList.getContext().getResources().getColor(R.color.red));

        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);

        Call<BusinessLeadDetailModalClass> call = simpleApi.businessRecieve(params);

        call.enqueue(new Callback<BusinessLeadDetailModalClass>() {
            @Override
            public void onResponse(Call<BusinessLeadDetailModalClass> call, Response<BusinessLeadDetailModalClass> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    if(response.body().getSendList() != null){

                        for(BusinessLeadDetailModalClass.Send businessReceiveList : response.body().getSendList()){

                            businessLeadSend.add(businessReceiveList);
                            binding.businessLeadSend.setVisibility(View.VISIBLE);
                            sendAdapter.updateBusinessLead(businessReceiveList);
                            binding.businessLeadSend.setHasFixedSize(true);
                            binding.businessLeadSend.setLayoutManager(new LinearLayoutManager(requireContext()));
                            binding.businessLeadSend.setAdapter(sendAdapter);
                        }
                    }else{
                        binding.businessLeadSend.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "No Data Is Available", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<BusinessLeadDetailModalClass> call, Throwable t) {
                call.cancel();

                progressDialog.dismiss();
            }
        });

    }

    void businessLeadReceive(){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrived Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        RecyclerView recyclerView = binding.businessLeadDetailRecyclerView;
        RecyclerView recyclerView2 = binding.businessLeadSend;

        Button btn_SendList = binding.btnSendList;
        Button btn_ReceiveList = binding.btnReceiveList;

        recyclerView2.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        btn_ReceiveList.setBackgroundColor(btn_ReceiveList.getContext().getResources().getColor(R.color.red));
        btn_SendList.setBackgroundColor(btn_SendList.getContext().getResources().getColor(R.color.lightGreyFont));

        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);

        Call<BusinessLeadDetailModalClass> call = simpleApi.businessRecieve(params);

        call.enqueue(new Callback<BusinessLeadDetailModalClass>() {
            @Override
            public void onResponse(Call<BusinessLeadDetailModalClass> call, Response<BusinessLeadDetailModalClass> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    businessLeadReceive.clear();

                    if(response.body().getReceiveList() != null){

                        for(BusinessLeadDetailModalClass.Receive businessReceiveList : response.body().getReceiveList()){

                            businessLeadReceive.add(businessReceiveList);
                            mAdapter.updateBusinessLead(businessReceiveList);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            recyclerView.setAdapter(mAdapter);
                        }
                    }else{

                        Toast.makeText(getActivity(), "No Data Is Available", Toast.LENGTH_SHORT).show();
                        recyclerView.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<BusinessLeadDetailModalClass> call, Throwable t) {
                call.cancel();

                progressDialog.dismiss();
                throw new RuntimeException("Test Crash for BceClube");
            }
        });
    }
}