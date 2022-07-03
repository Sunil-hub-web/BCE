package com.bceclub.app.Utils;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Models.DialogBoxModalClass;
import com.bceclub.app.Models.FavouriteConnectionModalClass;
import com.bceclub.app.Models.PaymentModalClass;
import com.bceclub.app.Models.ProfileModalClass;
import com.bceclub.app.Models.TenderListModalClass;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCalls {

    public ProfileModalClass profileModalClass;

    public ProfileModalClass getProfile(String id, APIResult result) {

        final ProfileModalClass[] profileModalClass = new ProfileModalClass[1];

        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", id);
        Call<ProfileModalClass> call = simpleApi.getProfile(params);
        call.enqueue(new Callback<ProfileModalClass>() {
            @Override
            public void onResponse(Call<ProfileModalClass> call, Response<ProfileModalClass> response) {
                if (response.isSuccessful()) {
                    profileModalClass[0] = response.body();
                    result.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProfileModalClass> call, Throwable t) {
                call.cancel();
            }
        });

        return profileModalClass[0];
    }

    public interface APIResult {
        void success(ProfileModalClass profileModalClass);

        void error(Throwable t);
    }

    void getTenderList() {
        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Call<TenderListModalClass> call = simpleApi.tenderList();
        call.enqueue(new Callback<TenderListModalClass>() {
            @Override
            public void onResponse(Call<TenderListModalClass> call, Response<TenderListModalClass> response) {

            }

            @Override
            public void onFailure(Call<TenderListModalClass> call, Throwable t) {
                call.cancel();
            }
        });
    }

    void getPaymentList(String id) {
        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", id);
        Call<PaymentModalClass> call = simpleApi.getPayment(params);
        call.enqueue(new Callback<PaymentModalClass>() {
            @Override
            public void onResponse(Call<PaymentModalClass> call, Response<PaymentModalClass> response) {

            }

            @Override
            public void onFailure(Call<PaymentModalClass> call, Throwable t) {
                call.cancel();
            }
        });
    }

    void acceptRequest(String user_id, String request_id) {
        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("request_id", request_id);
        Call<DialogBoxModalClass> call = simpleApi.acceptRequest(params);
//        ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Data Retrieved Please Wait...");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
       call.enqueue(new Callback<DialogBoxModalClass>() {
           @Override
           public void onResponse(Call<DialogBoxModalClass> call, Response<DialogBoxModalClass> response) {
               if (response.isSuccessful()){
                  // progressDialog.dismiss();
                   //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<DialogBoxModalClass> call, Throwable t) {
               //progressDialog.dismiss();
               call.cancel();
           }
       });
    }

    void favouriteConnection(String user_id) {
        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        Call<FavouriteConnectionModalClass> call = simpleApi.favouriteconnection(params);
//        ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Data Retrieved Please Wait...");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        call.enqueue(new Callback<FavouriteConnectionModalClass>() {
            @Override
            public void onResponse(Call<FavouriteConnectionModalClass> call, Response<FavouriteConnectionModalClass> response) {
                if (response.isSuccessful()){
                    // progressDialog.dismiss();
                    //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    for(FavouriteConnectionModalClass.RequestConnection requestConnection : response.body().getRequestConnection()){

                    }
                }
            }

            @Override
            public void onFailure(Call<FavouriteConnectionModalClass> call, Throwable t) {
                //progressDialog.dismiss();
                call.cancel();
            }
        });
    }




}
