package com.bceclub.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.bceclub.SessionManager;
import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Models.ResponseModalClass;
import com.bceclub.app.Models.LoginModalClass;
import com.bceclub.app.databinding.ActivityLoginBinding;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    SimpleApi simpleApi;

    SessionManager sessionManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sessionManager = new SessionManager(this);

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_MobileNumber = binding.Phone.getText().toString();

                if (str_MobileNumber.matches("^[0-9]*$")) {

                    if (str_MobileNumber.length() == 10) {

                        closeKeyboard();

                        if (validateFields()) {

                            if (isNetworkConnected()) {
                                progressDialog = new ProgressDialog(Login.this);
                                progressDialog.setMessage("Login Please Wait...");
                                progressDialog.setCanceledOnTouchOutside(false);
                                progressDialog.show();
                                simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("user_id", binding.Phone.getText().toString());
                                params.put("password", binding.password.getText().toString());
                                Call<LoginModalClass> call = simpleApi.login(params);
                                call.enqueue(new Callback<LoginModalClass>() {
                                    @Override
                                    public void onResponse(Call<LoginModalClass> call, Response<LoginModalClass> response) {
                                        if (response.isSuccessful()) {

                                            progressDialog.dismiss();

                                            Log.d("TAG", response.code() + "");
                                            LoginModalClass loginDetails = response.body();
                                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                                            sessionManager.setUSERID(response.body().id);
                                            sessionManager.setUSERNAME(response.body().name);
                                            sessionManager.setUSERMOBILENO(response.body().contact_no);
                                            sessionManager.setUSEREMAIL(response.body().email);
                                            sessionManager.setLogin();

                                            Intent intent = new Intent(Login.this, MainActivity.class);
                                            intent.putExtra("user_id", response.body().id);
                                            intent.putExtra("club_id", response.body().id);
                                            startActivity(intent);
                                            finish();


                                        } else {

                                            progressDialog.dismiss();

                                            Gson gson = new Gson();
                                            ResponseModalClass responseModalClass = gson.fromJson(response.errorBody().charStream(), ResponseModalClass.class);
                                            Toast.makeText(getApplicationContext(), responseModalClass.getMsg(), Toast.LENGTH_SHORT).show();

                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<LoginModalClass> call, Throwable t) {

                                        progressDialog.dismiss();
                                        call.cancel();
                                        throw new RuntimeException("Test Crash for BceClube"); // Force a crash
                                    }
                                });
                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else {

                        Toast.makeText(Login.this, "Enter 10 Digit Mobile No", Toast.LENGTH_SHORT).show();
                    }

                } else {



                    closeKeyboard();
                    if (validateFields()) {
                        progressDialog = new ProgressDialog(Login.this);
                        progressDialog.setMessage("Login Please Wait...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        if (isNetworkConnected()) {
                            simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user_id", binding.Phone.getText().toString());
                            params.put("password", binding.password.getText().toString());
                            Call<LoginModalClass> call = simpleApi.login(params);
                            call.enqueue(new Callback<LoginModalClass>() {
                                @Override
                                public void onResponse(Call<LoginModalClass> call, Response<LoginModalClass> response) {
                                    if (response.isSuccessful()) {

                                        progressDialog.dismiss();

                                        Log.d("TAG", response.code() + "");
                                        LoginModalClass loginDetails = response.body();
                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                                        sessionManager.setUSERID(response.body().id);
                                        sessionManager.setUSERNAME(response.body().name);
                                        sessionManager.setUSERMOBILENO(response.body().contact_no);
                                        sessionManager.setUSEREMAIL(response.body().email);
                                        sessionManager.setLogin();

                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        intent.putExtra("user_id", response.body().id);
                                        intent.putExtra("club_id", response.body().id);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        progressDialog.dismiss();

                                        Gson gson = new Gson();
                                        ResponseModalClass responseModalClass = gson.fromJson(response.errorBody().charStream(), ResponseModalClass.class);
                                        Toast.makeText(getApplicationContext(), responseModalClass.getMsg(), Toast.LENGTH_SHORT).show();

                                    }

                                }

                                @Override
                                public void onFailure(Call<LoginModalClass> call, Throwable t) {

                                    progressDialog.dismiss();

                                    call.cancel();

                                    throw new RuntimeException("Test Crash for BceClube"); // Force a crash
                                }
                            });
                        } else {

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
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

    Boolean validateFields() {
        if (binding.Phone.length() == 0) {
            binding.Phone.setError("This field is required");
            return false;

        }/*else if(binding.Phone.length() != 10){
            binding.Phone.setError("Mobile no. should be of 10 digits");
            return false;
        }*/

        if (binding.password.length() == 0) {
            binding.password.setError("This field is required");
            return false;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sessionManager.isLogin()) {

            startActivity(new Intent(Login.this, MainActivity.class));
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}