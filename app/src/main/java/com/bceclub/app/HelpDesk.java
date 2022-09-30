package com.bceclub.app;

import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Adapters.HelpDeskAdapter;
import com.bceclub.app.Models.DialogBoxModalClass;
import com.bceclub.app.Models.HelpDeskModalClass;
import com.bceclub.app.Models.ResponseModalClass;
import com.bceclub.app.databinding.FragmentHelpDeskBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpDesk extends Fragment {

    FragmentHelpDeskBinding binding;
    String user_id;
    ArrayList<HelpDeskModalClass.HelpDesk> helpDeskList = new ArrayList<>();

    Boolean isAllFieldChecked = false;

    Spinner helpDeskType;
    TextInputEditText message;
    AutoCompleteTextView helpType;
    Button addHelp;

    public HelpDesk() {
        // Required empty public constructor
    }

    public static HelpDesk newInstance(String param1, String param2) {
        HelpDesk fragment = new HelpDesk();
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

        HelpDeskAdapter mAdapter = new HelpDeskAdapter(helpDeskList);
        RecyclerView recyclerView = binding.helpDeskRecyckerView;

        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrieved Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.show();
        Call<HelpDeskModalClass> call = simpleApi.helpdesklist(params);
        call.enqueue(new Callback<HelpDeskModalClass>() {
            @Override
            public void onResponse(Call<HelpDeskModalClass> call, Response<HelpDeskModalClass> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    for (HelpDeskModalClass.HelpDesk helpDesk : response.body().getHelpDeskList()) {
                        helpDeskList.add(helpDesk);
                        mAdapter.updateHelpDesk(helpDesk);
                    }

                }else{

                    Gson gson = new Gson();
                    HelpDeskModalClass responseModalClass = gson.fromJson(response.errorBody().charStream(), HelpDeskModalClass.class);
                    Toast.makeText(getContext().getApplicationContext(), responseModalClass.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HelpDeskModalClass> call, Throwable t) {
                progressDialog.dismiss();
                call.cancel();
                throw new RuntimeException("Test Crash for BceClube");
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(mAdapter);

        binding.addGuestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder addNewHelp = new AlertDialog.Builder(getContext());
                View newHelp = getLayoutInflater().inflate(R.layout.add_help_dialog_box, null);
                message = newHelp.findViewById(R.id.helpDeskMessage);
                addHelp = newHelp.findViewById(R.id.addNewHelp);
                helpType = newHelp.findViewById(R.id.typeDropDown);
                String[] type = new String[]{"Membership", "Financial", "General", "Account Issue"};

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_menu_popup_item, type);
                helpType.setAdapter(adapter);

                addNewHelp.setView(newHelp);
                final AlertDialog alertDialog = addNewHelp.create();
                alertDialog.setCanceledOnTouchOutside(true);

                addHelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isAllFieldChecked = checkFields();
                        if (isAllFieldChecked) {
                            SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", user_id);
                            params.put("clb_id", "1");
                            params.put("hd_type", helpType.getText().toString());
                            params.put("hd_msg", message.getText().toString());

                            Call<DialogBoxModalClass> call = simpleApi.helpDeskSubmit(params);
                            call.enqueue(new Callback<DialogBoxModalClass>() {
                                @Override
                                public void onResponse(Call<DialogBoxModalClass> call, Response<DialogBoxModalClass> response) {
                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<DialogBoxModalClass> call, Throwable t) {
                                    call.cancel();
                                    throw new RuntimeException("Test Crash for BceClube");
                                }
                            });

                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog.show();
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
        binding = FragmentHelpDeskBinding.inflate(inflater, container, false);
        binding.toolbar.setTitle("Help Desk");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().onBackPressed();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,new more_frag()).commit();
            }
        });
        return binding.getRoot();
    }

    private Boolean checkFields() {
        if (message.length() == 0) {
            message.setError("This field is required");
            return false;
        }
        if (helpType.length() == 0) {
            helpType.setError("This field is required");
            return false;
        }

        return true;

    }

}