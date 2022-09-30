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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Adapters.memberAskAdapter;
import com.bceclub.app.Models.DialogBoxModalClass;
import com.bceclub.app.Models.HomeModalClass;
import com.bceclub.app.Models.MemberAskListModal;
import com.bceclub.app.Models.ProfileModalClass;
import com.bceclub.app.databinding.FragmentMemberAskBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class memberAsk extends Fragment {

    FragmentMemberAskBinding binding;
    SimpleApi simpleApi;
    ArrayList<MemberAskListModal.AskHistory> memberAskList = new ArrayList<>();

    TextInputEditText name, department, company, reason;
    Button askNewSubmitBtn;
    TextView meetingDate, clbId;

    Boolean isAllReviewFieldsChecked = false;

    String user_id;


    public memberAsk() {
        // Required empty public constructor
    }

    public static memberAsk newInstance(String param1, String param2) {
        memberAsk fragment = new memberAsk();
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
        RecyclerView recyclerView = binding.askRecyckerView;

        memberAskAdapter mAdapter = new memberAskAdapter(memberAskList);

        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        Call<MemberAskListModal> call = simpleApi.askHistoryList(params);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrieved Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        call.enqueue(new Callback<MemberAskListModal>() {
            @Override
            public void onResponse(Call<MemberAskListModal> call, Response<MemberAskListModal> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    for (MemberAskListModal.AskHistory memberAsk : response.body().getAskHistory()) {
                        memberAskList.add(memberAsk);
                        mAdapter.updateMemberAskList(memberAsk);
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberAskListModal> call, Throwable t) {
                progressDialog.dismiss();
                call.cancel();
                throw new RuntimeException("Test Crash for BceClube"); // Force a crash
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(mAdapter);
        super.onViewCreated(view, savedInstanceState);

        binding.askFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder askNewDialog = new AlertDialog.Builder(getContext());
                View newAskView = getLayoutInflater().inflate(R.layout.submit_ask_dialog_box, null);

                name = newAskView.findViewById(R.id.askName);
                department = newAskView.findViewById(R.id.askDepartment);
                company = newAskView.findViewById(R.id.askCompanyInp);
                reason = newAskView.findViewById(R.id.questionInp);
                askNewSubmitBtn = newAskView.findViewById(R.id.askNewSubmitButton);
                meetingDate = newAskView.findViewById(R.id.meetingDate);
                clbId = newAskView.findViewById(R.id.clubId);
                askNewDialog.setView(newAskView);
                final AlertDialog alertDialog = askNewDialog.create();
                alertDialog.setCanceledOnTouchOutside(true);

                SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);

                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);

                Call<HomeModalClass> call2 = simpleApi.getHome(params);
                call2.enqueue(new Callback<HomeModalClass>() {
                    @Override
                    public void onResponse(Call<HomeModalClass> call, Response<HomeModalClass> response) {
                        if (response.isSuccessful()) {
                            meetingDate.setText(response.body().getMeetingwise().getUpcomingMeetingDt());
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeModalClass> call, Throwable t) {
                        call.cancel();
                        throw new RuntimeException("Test Crash for BceClube"); // Force a crash

                    }
                });


                Call<ProfileModalClass> call = simpleApi.getProfile(params);
                call.enqueue(new Callback<ProfileModalClass>() {
                    @Override
                    public void onResponse(Call<ProfileModalClass> call, Response<ProfileModalClass> response) {
                        if (response.isSuccessful()) {
                            clbId.setText(response.body().getUserInfo().get(0).getClbId());
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileModalClass> call, Throwable t) {
                        call.cancel();
                        throw new RuntimeException("Test Crash for BceClube"); // Force a crash
                    }
                });

                askNewSubmitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isAllReviewFieldsChecked = CheckReviewFields();
                        if (isAllReviewFieldsChecked) {
                            Map<String, String> askNewParams = new HashMap<>();
                            askNewParams.put("user_id", user_id);

                            askNewParams.put("ur_clb_id", clbId.getText().toString());
                            askNewParams.put("meeting_dt", meetingDate.getText().toString());
                            askNewParams.put("ask_name", name.getText().toString());
                            askNewParams.put("ask_department", department.getText().toString());
                            askNewParams.put("ask_comp", company.getText().toString());
                            askNewParams.put("ask_question", reason.getText().toString());

                            Call<DialogBoxModalClass> call3 = simpleApi.submitAsk(askNewParams);
                            call3.enqueue(new Callback<DialogBoxModalClass>() {
                                @Override
                                public void onResponse(Call<DialogBoxModalClass> call, Response<DialogBoxModalClass> response) {
                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<DialogBoxModalClass> call, Throwable t) {
                                    call.cancel();
                                    throw new RuntimeException("Test Crash for BceClube"); // Force a crash
                                }
                            });
                            alertDialog.dismiss();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity activity = (MainActivity) getActivity();
        user_id = activity.getUserId();
        binding = FragmentMemberAskBinding.inflate(inflater, container, false);
        binding.toolbar.setTitle("Member Ask");
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

    boolean CheckReviewFields() {
        if (name.length() == 0) {
            name.setError("This field is required");
            return false;
        }

        if (department.length() == 0) {
            department.setError("This field is required");
            return false;
        }

        if (company.length() == 0) {
            company.setError("This field is required");
            return false;
        }

        if (reason.length() == 0) {
            reason.setError("This field is required");
            return false;
        }
        return true;
    }
}