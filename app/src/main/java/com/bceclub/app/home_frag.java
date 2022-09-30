package com.bceclub.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.bceclub.SessionManager;
import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Models.HomeModalClass;
import com.bceclub.app.databinding.FragmentHomeFragBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_frag extends Fragment {

    FragmentHomeFragBinding binding;
    String user_id;
    SessionManager sessionManager;


    public home_frag() {
        // Required empty public constructor
    }


    public static home_frag newInstance(String param1, String param2) {
        home_frag fragment = new home_frag();
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

        setData();
        String img_url1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiTVNHhNbyNuGl2NnIeaC72wk4zvapPEYOjcu6Wf3xYjPUaNpdWWdp-EI80NUsvVSzLw&usqp=CAU";
        String img_url2 = "https://thumbs.dreamstime.com/b/business-development-to-success-growth-banking-financial-global-network-businessman-hold-pointing-arrow-up-graph-227718315.jpg";
        String img_url3 = "https://www.silworld.in/wp-content/uploads/2019/10/career-banner-compressor.jpg";
        String img_url4 = "https://www.investni.com/sites/default/files/2020-06/business_support_investment_banner_904x466.jpg";
        String img_url5 = "https://researchleap.com/wp-content/uploads/2019/10/shutterstock_718547992-min-e1571736994121.jpg";

        ImageSlider imageSlider = binding.viewPager;
        List<SlideModel> imageList = new ArrayList<SlideModel>();
        imageList.add(new SlideModel(img_url1, ScaleTypes.FIT));
        imageList.add(new SlideModel(img_url2, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(img_url3, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(img_url4, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(img_url5, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imageList);


        binding.reviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_home_frag_to_reviewFragment);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ReviewFragment()).commit();
            }
        });

        binding.businessReceivedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_home_frag_to_businessLeadDetailFragment);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new BusinessLeadDetailFragment()).commit();
            }
        });

        binding.leadGivenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_home_frag_to_businessLeadDetailFragment);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new BusinessLeadDetailFragment()).commit();
            }
        });

        binding.guestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_home_frag_to_guestList);
//                getActivity().getSupportFragmentManager().popBackStack();

                GuestList guestList = new GuestList();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, guestList).addToBackStack(null).commit();

            }
        });

        binding.businessGivenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_home_frag_to_guestList);
//                getActivity().getSupportFragmentManager().popBackStack();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new BusinessGiven()).addToBackStack(null).commit();

            }
        });

        binding.connectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FavouriteConnections()).addToBackStack(null).commit();

            }
        });

        binding.requestAlertLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new RequestAlert()).addToBackStack(null).commit();

            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog_Logout();
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    void setData() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Home Page Details Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        Call<HomeModalClass> call = simpleApi.getHome(params);
        call.enqueue(new Callback<HomeModalClass>() {
            @Override
            public void onResponse(Call<HomeModalClass> call, Response<HomeModalClass> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    HomeModalClass homeModalClass = response.body();
                    binding.nextMeetingDate.setText(response.body().getMeetingwise().getUpcomingMeetingDt());
                    binding.businessGiven.setText(String.valueOf((int) Double.parseDouble(homeModalClass.getTotalinfo().getTotalBusinessGiven())));
                    binding.businessReceived.setText(String.valueOf((int) Double.parseDouble(homeModalClass.getTotalinfo().getTotalBusinessReceive())));
                    binding.leadGiven.setText((homeModalClass.getTotalinfo().getNoOfLeadGiven()));
                    binding.review.setText((homeModalClass.getTotalinfo().getTotalReview()));
                    binding.guest.setText((homeModalClass.getTotalinfo().getTotalGuestList()));
                    binding.requestAlert.setText((homeModalClass.getMeetingwise().getRequestAlert()));
                }
            }

            @Override
            public void onFailure(Call<HomeModalClass> call, Throwable t) {
                call.cancel();

                progressDialog.dismiss();

                throw new RuntimeException("Test Crash for BceClube");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.getSupportActionBar().setTitle("BCE Bhubaneswar");
//        activity.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
//        activity.getSupportActionBar().setCustomView(R.layout.custom_home_action_bar);

        //activity.getSupportActionBar().setLogo(R.drawable.logo);

        binding = FragmentHomeFragBinding.inflate(inflater, container, false);
        MainActivity activity = (MainActivity) getActivity();
        user_id = activity.getUserId();
        return binding.getRoot();
    }

    public void openDialog_Logout(){

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.logout_logout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        MaterialButton cancelBtn = dialog.findViewById(R.id.cancelBtn);
        MaterialButton saveBtn = dialog.findViewById(R.id.saveBtn);
        TextView showtext = dialog.findViewById(R.id.showtext);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sessionManager = new SessionManager(getActivity());
                sessionManager.logoutUser();
            }
        });

        dialog.show();


    }
}