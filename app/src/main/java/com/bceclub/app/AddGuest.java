package com.bceclub.app;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Models.DialogBoxModalClass;
import com.bceclub.app.databinding.FragmentAddGuestBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGuest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGuest extends Fragment {

    FragmentAddGuestBinding binding;
    String user_id;
    SimpleApi simpleApi;
    Boolean isAllFieldsChecked;
    DatePickerDialog.OnDateSetListener setListener;
    int year, month, day, hour, minute;
    String value, date, time;

    public AddGuest() {
        // Required empty public constructor
    }

    public static AddGuest newInstance(String param1, String param2) {
        AddGuest fragment = new AddGuest();
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

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        //binding.datatime.setEnabled(false);

        date = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date());

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                String date = year+"/"+month+"/"+day;
                //String date = year+"-"+month+"-"+day;
                binding.datatime.setText(date);

            }
        };

        binding.datatime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCalender1();
            }
        });

        binding.addGuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAllFieldsChecked = CheckOtherFeilds();
                if (isAllFieldsChecked) {

                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    closeKeyboard();
                   // user_id = AddGuestArgs.fromBundle(getArguments()).getUserId();
                    MainActivity activity = (MainActivity) getActivity();
                    user_id = activity.getUserId();
                    simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", user_id);
                    params.put("g_name", binding.nameInp.getText().toString());
                    params.put("g_email", binding.gmailInp.getText().toString());
                    params.put("g_phone", binding.phoneInp.getText().toString());
                    params.put("g_business", binding.businessInp.getText().toString());
                    params.put("g_meeting_dt", binding.datatime.getText().toString().trim());
                    Call<DialogBoxModalClass> call = simpleApi.addGuest(params);

                    call.enqueue(new Callback<DialogBoxModalClass>() {
                        @Override
                        public void onResponse(Call<DialogBoxModalClass> call, Response<DialogBoxModalClass> response) {

                            progressDialog.dismiss();

                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                //Navigation.findNavController(binding.getRoot()).navigate(R.id.action_addGuest_to_guestList);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new GuestList()).commit();
                            }
                            else {

                                Toast.makeText(getContext(), response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DialogBoxModalClass> call, Throwable t) {
                            call.cancel();

                            progressDialog.dismiss();
                            throw new RuntimeException("Test Crash for BceClube");

                        }
                    });
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddGuestBinding.inflate(inflater, container, false);

        binding.toolbar.setTitle("Add Guest List");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new GuestList()).commit();
            }
        });

        return binding.getRoot();


    }

    private Boolean CheckOtherFeilds() {
        if (binding.nameInp.length() == 0) {
            binding.nameInp.setError("This field is required");
            return false;
        }
        if (binding.phoneInp.length() == 0) {
            binding.phoneInp.setError("This field is required");
            return false;
        } else if (binding.phoneInp.length() != 10) {
            binding.phoneInp.setError("Phone no. must be of 10 digits");
            return false;
        }
        if (binding.gmailInp.length() == 0) {
            binding.gmailInp.setError("This field is required");
            return false;
        }

        if (binding.businessInp.length() == 0) {
            binding.businessInp.setError("This field is required");
            return false;
        }
//        if (binding.date.is == 0) {
//            binding.date.setError("This field is required");
//            return false;
//        }
        return true;
    }


    private void closeKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    public void showCalender1(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month = month+1;

                String fmonth=""+month;
                String fDate=""+dayOfMonth;

                if(month<10){
                    fmonth ="0"+month;
                }
                if (dayOfMonth<10){
                    fDate="0"+dayOfMonth;
                }

                String date = year+"-"+fmonth+"-"+fDate;
                //String date = year+"-"+month+"-"+day;
                binding.datatime.setText(date);

            }
        },year,month,day);

        datePickerDialog.show();
    }

}