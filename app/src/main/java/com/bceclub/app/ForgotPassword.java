package com.bceclub.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    SimpleApi simpleApi;

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
                                }
                            });

                        } else {

                            Toast.makeText(ForgotPassword.this, "Enter 10 Digit Mobile No", Toast.LENGTH_SHORT).show();
                        }
                    } else if (isValidEmail(str_MobileNumber)) {

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
}