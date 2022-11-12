package com.bceclub.app;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Adapters.GuestListAdapter;
import com.bceclub.app.Models.GuestListModalClass;
import com.bceclub.app.databinding.FragmentGuestListBinding;
import com.bceclub.app.databinding.FragmentHomeFragBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestList extends Fragment {

    FragmentGuestListBinding binding;
    SimpleApi simpleApi;
    GuestListAdapter mAdapter;
    ArrayList<GuestListModalClass.Guest> guestList = new ArrayList<>();
    String user_id;

    public GuestList() {
        // Required empty public constructor
    }

    public static GuestList newInstance(String param1, String param2) {
        GuestList fragment = new GuestList();
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

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Retrive Guest List Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        RecyclerView recyclerView = binding.guestListRecyclerView;
        mAdapter = new GuestListAdapter(guestList);

        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);


        Call<GuestListModalClass> call = simpleApi.guestList(params);
        call.enqueue(new Callback<GuestListModalClass>() {
            @Override
            public void onResponse(Call<GuestListModalClass> call, Response<GuestListModalClass> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();

                    try{
                        if(response.body() != null){

                            if (response.body().getGuestList() == null){

                                Toast.makeText(getContext(), "No Guests", Toast.LENGTH_SHORT).show();

                            } else {

                                guestList.clear();

                                for (GuestListModalClass.Guest guest : response.body().getGuestList()) {

                                    guestList.add(guest);
                                    mAdapter.updateGuestList(guest);
                                }
                            }

                        }else{

                            Toast.makeText(getActivity(), "Data Is Not Avilable", Toast.LENGTH_SHORT).show();
                        }

                    }catch(Exception e){

                        e.printStackTrace();

                        throw new RuntimeException("Test Crash for BceClube"+e);

                    }
                }
            }

            @Override
            public void onFailure(Call<GuestListModalClass> call, Throwable t) {
                call.cancel();

                progressDialog.dismiss();
                throw new RuntimeException("Test Crash for BceClube");
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(mAdapter);

        binding.addGuestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                user_id = activity.getUserId();
                NavDirections navDirections = GuestListDirections.actionGuestListToAddGuest(user_id);
                AddGuest addGuest = new AddGuest();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, addGuest).commit();

//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_guestList_to_addGuest);
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
        binding = FragmentGuestListBinding.inflate(inflater, container, false);
        binding.toolbar.setTitle("Guest List");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FragmentTransaction ft = getFragmentManager().beginTransaction();
                 ft.replace(R.id.fragment, new home_frag()).commit();
            }
        });
        return binding.getRoot();
    }
}