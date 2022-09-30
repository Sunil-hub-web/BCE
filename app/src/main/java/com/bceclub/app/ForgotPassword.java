package com.bceclub.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Models.ResponseModalClass;
import com.bceclub.app.databinding.ActivityForgotPasswordBinding;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    SimpleApi simpleApi;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //getSupportActionBar().hide();

        binding.backButtonForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        binding.submitBtnForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeKeyboard();
                if (binding.PhoneForgotPwd.getText().toString().length() != 0) {

                    String str_MobileNumber = binding.PhoneForgotPwd.getText().toString();

                    if (str_MobileNumber.matches("^[0-9]*$")) {

                        if (str_MobileNumber.length() == 10) {

                            timer();

                            ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
                            progressDialog.setMessage("Processing...");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();

                            simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                            Map<String, String> params = new HashMap<>();
                            params.put("type", "send_otp");
                            params.put("email", binding.PhoneForgotPwd.getText().toString());
                            Call<ResponseModalClass> call = simpleApi.forgotPass(params);
                            call.enqueue(new Callback<ResponseModalClass>() {
                                @Override
                                public void onResponse(Call<ResponseModalClass> call, Response<ResponseModalClass> response) {
                                    if (response.isSuccessful()) {

                                        progressDialog.dismiss();

                                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ForgotPassword.this, verifyOTP.class);
                                        intent.putExtra("email", binding.PhoneForgotPwd.getText().toString());
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        progressDialog.dismiss();
                                        //Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                        Gson gson = new Gson();
                                        ResponseModalClass responseModalClass = gson.fromJson(response.errorBody().charStream(), ResponseModalClass.class);
                                        Toast.makeText(getApplicationContext(), responseModalClass.getMsg(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<ResponseModalClass> call, Throwable t) {
                                    call.cancel();

                                    progressDialog.dismiss();
                                    throw new RuntimeException("Test Crash for BceClube");
                                }
                            });

                        } else {

                            Toast.makeText(ForgotPassword.this, "Enter 10 Digit Mobile No", Toast.LENGTH_SHORT).show();
                        }
                    } else if (isValidEmail(str_MobileNumber)) {

                        timer();

                        ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
                        progressDialog.setMessage("Processing...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                        Map<String, String> params = new HashMap<>();
                        params.put("type", "send_otp");
                        params.put("email", binding.PhoneForgotPwd.getText().toString());
                        Call<ResponseModalClass> call = simpleApi.forgotPass(params);
                        call.enqueue(new Callback<ResponseModalClass>() {
                            @Override
                            public void onResponse(Call<ResponseModalClass> call, Response<ResponseModalClass> response) {
                                if (response.isSuccessful()) {

                                    progressDialog.dismiss();

                                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgotPassword.this, verifyOTP.class);
                                    intent.putExtra("email", binding.PhoneForgotPwd.getText().toString());
                                    startActivity(intent);
                                    finish();

                                } else {

                                    progressDialog.dismiss();
                                    //Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    Gson gson = new Gson();
                                    ResponseModalClass responseModalClass = gson.fromJson(response.errorBody().charStream(), ResponseModalClass.class);
                                    Toast.makeText(getApplicationContext(), responseModalClass.getMsg(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseModalClass> call, Throwable t) {
                                call.cancel();

                                progressDialog.dismiss();
                                throw new RuntimeException("Test Crash for BceClube");
                            }
                        });
                    } else {

                        Toast.makeText(ForgotPassword.this, "Enter Valide Mobile No Or EmailId", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.PhoneForgotPwd.setError("This field is required!");
                }

            }
        });

        binding.resendTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeKeyboard();
                if (binding.PhoneForgotPwd.getText().toString().length() != 0) {

                    String str_MobileNumber = binding.PhoneForgotPwd.getText().toString();

                    if (str_MobileNumber.matches("^[0-9]*$")) {

                        if (str_MobileNumber.length() == 10) {

                            timer();

                            ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
                            progressDialog.setMessage("Processing...");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();

                            simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                            Map<String, String> params = new HashMap<>();
                            params.put("type", "send_otp");
                            params.put("email", binding.PhoneForgotPwd.getText().toString());
                            Call<ResponseModalClass> call = simpleApi.forgotPass(params);
                            call.enqueue(new Callback<ResponseModalClass>() {
                                @Override
                                public void onResponse(Call<ResponseModalClass> call, Response<ResponseModalClass> response) {
                                    if (response.isSuccessful()) {

                                        progressDialog.dismiss();

                                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ForgotPassword.this, verifyOTP.class);
                                        intent.putExtra("email", binding.PhoneForgotPwd.getText().toString());
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        progressDialog.dismiss();
                                        //Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                        Gson gson = new Gson();
                                        ResponseModalClass responseModalClass = gson.fromJson(response.errorBody().charStream(), ResponseModalClass.class);
                                        Toast.makeText(getApplicationContext(), responseModalClass.getMsg(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<ResponseModalClass> call, Throwable t) {
                                    call.cancel();

                                    progressDialog.dismiss();
                                    throw new RuntimeException("Test Crash for BceClube");
                                }
                            });

                        } else {

                            Toast.makeText(ForgotPassword.this, "Enter 10 Digit Mobile No", Toast.LENGTH_SHORT).show();
                        }
                    } else if (isValidEmail(str_MobileNumber)) {

                        timer();

                        ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
                        progressDialog.setMessage("Processing...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                        Map<String, String> params = new HashMap<>();
                        params.put("type", "send_otp");
                        params.put("email", binding.PhoneForgotPwd.getText().toString());
                        Call<ResponseModalClass> call = simpleApi.forgotPass(params);
                        call.enqueue(new Callback<ResponseModalClass>() {
                            @Override
                            public void onResponse(Call<ResponseModalClass> call, Response<ResponseModalClass> response) {
                                if (response.isSuccessful()) {

                                    progressDialog.dismiss();

                                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgotPassword.this, verifyOTP.class);
                                    intent.putExtra("email", binding.PhoneForgotPwd.getText().toString());
                                    startActivity(intent);
                                    finish();

                                } else {

                                    progressDialog.dismiss();
                                    //Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    Gson gson = new Gson();
                                    ResponseModalClass responseModalClass = gson.fromJson(response.errorBody().charStream(), ResponseModalClass.class);
                                    Toast.makeText(getApplicationContext(), responseModalClass.getMsg(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseModalClass> call, Throwable t) {
                                call.cancel();

                                progressDialog.dismiss();
                                throw new RuntimeException("Test Crash for BceClube");
                            }
                        });
                    } else {

                        Toast.makeText(ForgotPassword.this, "Enter Valide Mobile No Or EmailId", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.PhoneForgotPwd.setError("This field is required!");
                }

            }
        });

        binding.backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
                finish();
            }
        });


    }

    Boolean validateFeild() {
        if (binding.PhoneForgotPwd.getText().toString().isEmpty()) {
            binding.PhoneForgotPwd.setError("This Field is required !");
        }
        return true;
    }

    private void closeKeyboard() {

        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void timer(){

        //Initialize time duration
        long duration = TimeUnit.MINUTES.toMillis(1);
        //Initialize countdown timer

        new CountDownTimer(duration, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

                //When tick
                //Convert millisecond to minute and second

                String sDuration = String.format(Locale.ENGLISH,"%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                binding.resendTimer.setText("Resend : "+sDuration);
                binding.resendTimer.setEnabled(false);

            }

            @Override
            public void onFinish() {

               // binding.resendTimer.setVisibility(View.GONE);
                binding.resendTimer.setEnabled(true);
                binding.resendTimer.setText("Resend");

            }
        }.start();
    }
}