package com.bceclub.app;

import static com.bceclub.app.MainActivity.member_id;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bceclub.SessionManager;
import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Models.DialogBoxModalClass;
import com.bceclub.app.Models.ProfileModalClass;
import com.bceclub.app.Models.SendReviewModalClass;
import com.bceclub.app.databinding.FragmentBusinessLeadDetailBinding;
import com.bceclub.app.databinding.FragmentMemberDetailsBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

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
 * Use the {@link MemberDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberDetails extends Fragment {

    private FragmentMemberDetailsBinding binding;
    TextInputEditText name;
    TextInputEditText mobile;
    TextInputEditText action;
    TextInputEditText remarks;
    RatingBar sendLeadRating;
    TextView leadRatingText;

    SimpleApi simpleApi;

    TextInputEditText amount;
    TextInputEditText invoice;
    TextInputEditText remarksThanksNote;

    TextInputEditText remarksReview;
    RatingBar reviewRating;
    TextView ratingText;

    String user_id;
    String member_ID;


    Button newLeadBtn;
    Button thankNoteBtn;
    Button newReviewBtn;


    Boolean isAllBusinessLeadFieldChecked = false;
    Boolean isAllThankNoteFieldChecked = false;
    Boolean isAllReviewFieldsChecked = false;

    int year, month, day, hour, minute;
    String value, date, time;
    DatePickerDialog.OnDateSetListener setListener;

    public MemberDetails() {
        // Required empty public constructor
    }

    public static MemberDetails newInstance(String param1, String param2) {
        MemberDetails fragment = new MemberDetails();
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

//        member_ID = MemberDetailsArgs.fromBundle(getArguments()).getMemberID();
        member_ID = member_id;
        setData(member_ID);

        binding.connectionRequestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onetoone();


                /*simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                Map<String, String> params = new HashMap<>();
                params.put("member_id", member_ID);
                params.put("user_id", user_id);
                Call<DialogBoxModalClass> call = simpleApi.sendRequest(params);
                call.enqueue(new Callback<DialogBoxModalClass>() {
                    @Override
                    public void onResponse(Call<DialogBoxModalClass> call, Response<DialogBoxModalClass> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DialogBoxModalClass> call, Throwable t) {
                        call.cancel();
                        throw new RuntimeException("Test Crash for BceClube"); // Force a crash
                    }
                });
*/
            }
        });

        binding.businessLeadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder sendNewBusinessLeadAlert = new AlertDialog.Builder(getContext());
                View newBusinessLeadView = getLayoutInflater().inflate(R.layout.new_business_lead_dialog_box, null);
                name = newBusinessLeadView.findViewById(R.id.nameInp);
                mobile = newBusinessLeadView.findViewById(R.id.mobileInp);
                action = newBusinessLeadView.findViewById(R.id.businessLeadInp);
                remarks = newBusinessLeadView.findViewById(R.id.remarkInp);
                newLeadBtn = newBusinessLeadView.findViewById(R.id.sendNewBusinessLeadBtn);
                sendLeadRating = newBusinessLeadView.findViewById(R.id.LeadRating);
                leadRatingText = newBusinessLeadView.findViewById(R.id.leadRatingText);
                sendNewBusinessLeadAlert.setView(newBusinessLeadView);
                final AlertDialog alertDialog = sendNewBusinessLeadAlert.create();
                alertDialog.setCanceledOnTouchOutside(true);
                sendLeadRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        leadRatingText.setText(String.valueOf((int) ratingBar.getRating()) + "/5");
                    }
                });

                newLeadBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isAllBusinessLeadFieldChecked = CheckBusinessLeadFields();
                        if (isAllBusinessLeadFieldChecked) {

                            simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", user_id);
                            params.put("ref_to_urid", member_ID);
                            params.put("ref_title", action.getText().toString());
                            params.put("ref_name", name.getText().toString());
                            params.put("ref_mobile", mobile.getText().toString());
                            params.put("ref_rating", String.valueOf((int) sendLeadRating.getRating()));
                            params.put("ref_remark", remarks.getText().toString());

                            Call<DialogBoxModalClass> call = simpleApi.SendLead(params);
                            call.enqueue(new Callback<DialogBoxModalClass>() {
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
                        }
                    }
                });
                alertDialog.show();
            }
        });

        binding.thanksNoteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder thankNoteAlert = new AlertDialog.Builder(getContext());
                View thankNoteView = getLayoutInflater().inflate(R.layout.thanks_note_dialog_box, null);
                amount = thankNoteView.findViewById(R.id.amountInp);
                invoice = thankNoteView.findViewById(R.id.invoiceFile);
                remarksThanksNote = thankNoteView.findViewById(R.id.remarkInpThank);
                thankNoteBtn = thankNoteView.findViewById(R.id.thankNoteSubmit);
                thankNoteAlert.setView(thankNoteView);
                final AlertDialog alertDialog = thankNoteAlert.create();
                alertDialog.setCanceledOnTouchOutside(true);


                thankNoteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isAllThankNoteFieldChecked = CheckThankNoteFields();
                        if (isAllThankNoteFieldChecked) {
                            simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", user_id);
                            params.put("tl_to_urid", member_ID);
                            params.put("tl_amount", amount.getText().toString());
                            params.put("tl_remark", remarksThanksNote.getText().toString());

                            Call<DialogBoxModalClass> call = simpleApi.sendThankNote(params);
                            call.enqueue(new Callback<DialogBoxModalClass>() {
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

                        }
                    }
                });
                alertDialog.show();
            }
        });

        binding.reviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder sendNewReviewAlert = new AlertDialog.Builder(getContext());
                View newReviewView = getLayoutInflater().inflate(R.layout.new_review_dialog_box, null);

                remarksReview = newReviewView.findViewById(R.id.remarkInpReview);
                reviewRating = newReviewView.findViewById(R.id.reviewRating);
                newReviewBtn = newReviewView.findViewById(R.id.newReviewBtn);
                ratingText = newReviewView.findViewById(R.id.ratingNumber);
                sendNewReviewAlert.setView(newReviewView);
                final AlertDialog alertDialog = sendNewReviewAlert.create();
                alertDialog.setCanceledOnTouchOutside(true);
                reviewRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        ratingText.setText(String.valueOf((int) ratingBar.getRating()) + "/5");
                    }
                });

                newReviewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isAllReviewFieldsChecked = CheckReviewFields();
                        if (isAllReviewFieldsChecked) {
                            Map<String, String> reviewParams = new HashMap<>();
                            reviewParams.put("user_id", user_id);
                            reviewParams.put("rv_to_urid", member_ID);
                            reviewParams.put("rv_rating", String.valueOf((int) reviewRating.getRating()));
                            reviewParams.put("rv_remark", remarksReview.getText().toString());

                            SendReviewModalClass sendReviewModalClass = new SendReviewModalClass(user_id, member_ID,
                                    String.valueOf((int) reviewRating.getRating()), remarksReview.getText().toString());

                            Call<DialogBoxModalClass> call = simpleApi.sendReview(reviewParams);
                            call.enqueue(new Callback<DialogBoxModalClass>() {
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


                        }
                    }
                });
                alertDialog.show();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    void setData(String memberId) {
        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", memberId);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrieved Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<ProfileModalClass> call = simpleApi.getProfile(params);
        call.enqueue(new Callback<ProfileModalClass>() {
            @Override
            public void onResponse(Call<ProfileModalClass> call, Response<ProfileModalClass> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();


                    ProfileModalClass profileModalClass = response.body();

                    if (!profileModalClass.getImg().isEmpty())
                        Picasso.get().load(profileModalClass.getImg()).into(binding.memberProfilePic, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                binding.progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                    else
                        Picasso.get().load("https://www.freeiconspng.com/uploads/customers-icon-3.png").into(binding.memberProfilePic);
                    binding.nameProfile.setText(profileModalClass.getUserInfo().get(0).getUrName());
                    binding.clubNameProfile.setText("Club: " + profileModalClass.getUserInfo().get(0).getClbName());
                    binding.CompanyProfile.setText("Company: " + profileModalClass.getUserInfo().get(0).getCdCompany());
                    binding.memberDesignation.setText(profileModalClass.getUserInfo().get(0).getCdDesig());

                    binding.address.setText(profileModalClass.getUserInfo().get(0).getUrAddress());
                    binding.email.setText(profileModalClass.getUserInfo().get(0).getUrEmail());
                    binding.phone.setText(profileModalClass.getUserInfo().get(0).getUrMobile());
                    binding.designation.setText(profileModalClass.getUserInfo().get(0).getCdDesig());

                    binding.otherOrganization.setText(profileModalClass.getUserInfo().get(0).getCdAssorg());
                    binding.businessDetail.setText(profileModalClass.getUserInfo().get(0).getCdBusinessDetail());
                    binding.exp.setText(profileModalClass.getUserInfo().get(0).getCdExperiences());

                }
            }

            @Override
            public void onFailure(Call<ProfileModalClass> call, Throwable t) {
                progressDialog.dismiss();
                call.cancel();
                throw new RuntimeException("Test Crash for BceClube"); // Force a crash
            }
        });

    }

    private Boolean CheckBusinessLeadFields() {
        if (name.length() == 0) {
            name.setError("This field is required");
            return false;
        }
        if (mobile.length() == 0) {
            mobile.setError("This field is required");
            return false;
        } else if (mobile.length() != 10) {
            mobile.setError("phone no. must contain 10 digits");
            return false;
        }
        if (action.length() == 0) {
            action.setError("This field is required");
            return false;
        }
        if (remarks.length() == 0) {
            remarks.setError("This field is required");
            return false;
        }

        return true;

    }

    private Boolean CheckThankNoteFields() {
        if (amount.length() == 0) {
            amount.setError("This field is required");
            return false;
        }
        if (invoice.length() == 0) {
            invoice.setError("This field is required");
            return false;
        }
        if (remarksThanksNote.length() == 0) {
            remarksThanksNote.setError("This field is required");
            return false;
        }
        return true;

    }

    private Boolean CheckReviewFields() {

        if (remarksReview.length() == 0) {
            remarksReview.setError("This field is required");
            return false;
        }
        return true;

    }

    private void closeKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    public void onetoone(){

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.onetooneconn);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        EditText date_d = dialog.findViewById(R.id.date);
        EditText time_t = dialog.findViewById(R.id.time);
        EditText remarkes = dialog.findViewById(R.id.remarkes);
        Button btn_sendonetonne = dialog.findViewById(R.id.btn_sendonetonne);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        //binding.datatime.setEnabled(false);

        date = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date());

       /* setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                String date = year+"/"+month+"/"+day;
                //String date = year+"-"+month+"-"+day;
                date_d.setText(date);

            }
        };*/

        date_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month + 1;
                        String date = dayOfMonth + "-" + month + "-" + year;
                        //String date = year+"-"+month+"-"+day;
                        date_d.setText(date);
                    }
                }, year, month, day);

                //display previous selected date
                // datePickerDialog.updateDate(year, month, day);

                //disiable past date
                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();

            }
        });

        time_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        int str_hour = hourOfDay;
                        int str_minute = minute;

                        Calendar calendar1 = Calendar.getInstance();

                        String sDate = date;

                        String[] string = sDate.split("/");

                        int sDay = Integer.parseInt(string[0]);

                        calendar1.set(Calendar.DAY_OF_MONTH, sDay);
                        calendar1.set(Calendar.HOUR_OF_DAY, str_hour);
                        calendar1.set(Calendar.MINUTE, str_minute);

                        time_t.setText(android.text.format.DateFormat.format("hh:mm aa", calendar1));

                        if (calendar1.getTimeInMillis() == Calendar.getInstance().getTimeInMillis()) {

                            Toast.makeText(getContext(), "Current Time selected", Toast.LENGTH_SHORT).show();

                        } else if (calendar1.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {

                            Toast.makeText(getContext(), "Future time selected", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getContext(), "Past time selected", Toast.LENGTH_LONG).show();
                        }


                    }
                }, hour, minute, false);


                timePickerDialog.show();
            }
        });

        btn_sendonetonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });


        dialog.show();


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
               // binding.datatime.setText(date);

            }
        },year,month,day);

        datePickerDialog.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity activity = (MainActivity) getActivity();
        user_id = activity.getUserId();
        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        binding = FragmentMemberDetailsBinding.inflate(inflater, container, false);
        binding.toolbar.setTitle("Member Detals");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().onBackPressed();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new member_frag()).commit();
            }
        });
        return binding.getRoot();
    }
}