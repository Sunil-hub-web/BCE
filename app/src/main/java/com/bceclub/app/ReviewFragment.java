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
import android.widget.Toast;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Adapters.ReviewListAdapter;
import com.bceclub.app.Models.ReviewItem;
import com.bceclub.app.Models.ReviewListModalClass;
import com.bceclub.app.databinding.FragmentHomeFragBinding;
import com.bceclub.app.databinding.FragmentReviewBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment {

    FragmentReviewBinding binding;
    SimpleApi simpleApi;
    String user_id;
    ReviewListAdapter mReviewAdapter;


    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance(String param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
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

        sentReviewList();

        binding.receiveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getResources().getColor(R.color.red));
                binding.sendList.setBackgroundColor(getResources().getColor(R.color.darkGreyFont));
                receivedReviewList();
            }
        });

        binding.sendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getResources().getColor(R.color.red));
                binding.receiveList.setBackgroundColor(getResources().getColor(R.color.darkGreyFont));
                sentReviewList();
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    void sentReviewList() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrieved Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        ArrayList<ReviewItem> reviewList = new ArrayList<>();
        RecyclerView recyclerView = binding.reviewsRecyclerView;
        mReviewAdapter = new ReviewListAdapter(reviewList);

        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);

        reviewList.clear();

        Call<ReviewListModalClass> call = simpleApi.reviewList(params);

        call.enqueue(new Callback<ReviewListModalClass>() {
            @Override
            public void onResponse(Call<ReviewListModalClass> call, Response<ReviewListModalClass> response) {

                reviewList.clear();

                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    if (response.body().getSendList() != null) {

                        for (ReviewItem reviewItem : response.body().getSendList()) {
                            reviewList.add(reviewItem);
                            recyclerView.setVisibility(View.VISIBLE);
                            mReviewAdapter.updateReview(reviewItem);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            recyclerView.setAdapter(mReviewAdapter);
                        }
                    }
                }else{

                    Toast.makeText(getActivity(), "No Data Is Available", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ReviewListModalClass> call, Throwable t) {
                progressDialog.dismiss();
                call.cancel();
                throw new RuntimeException("Test Crash for BceClube"); // Force a crash
            }
        });
    }

    void receivedReviewList() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrieved Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        ArrayList<ReviewItem> reviewList = new ArrayList<>();
        RecyclerView recyclerView = binding.reviewsRecyclerView;
        mReviewAdapter = new ReviewListAdapter(reviewList);

        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);

        Call<ReviewListModalClass> call = simpleApi.reviewList(params);

        call.enqueue(new Callback<ReviewListModalClass>() {
            @Override
            public void onResponse(Call<ReviewListModalClass> call, Response<ReviewListModalClass> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    reviewList.clear();

                    if (response.body().getReceiveList() != null) {
                        for (ReviewItem reviewItem : response.body().getReceiveList()) {
                            reviewList.add(reviewItem);
                            recyclerView.setVisibility(View.VISIBLE);
                            mReviewAdapter.updateReview(reviewItem);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            recyclerView.setAdapter(mReviewAdapter);
                        }
                    }
                }else{

                    Toast.makeText(getActivity(), "No Data Is Available", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ReviewListModalClass> call, Throwable t) {
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
        binding = FragmentReviewBinding.inflate(inflater, container, false);
        MainActivity activity = (MainActivity) getActivity();
        user_id = activity.getUserId();

        binding.toolbar.setTitle("Reviews");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().onBackPressed();

               FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, new home_frag()).commit();
            }
        });
        return binding.getRoot();
    }
}