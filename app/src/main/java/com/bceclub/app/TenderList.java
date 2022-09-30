package com.bceclub.app;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bceclub.app.API.RetrofitInstance;
import com.bceclub.app.API.SimpleApi;
import com.bceclub.app.Models.TenderListModalClass;
import com.bceclub.app.databinding.FragmentTenderListBinding;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TenderList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TenderList extends Fragment {

    public TenderList() {
        // Required empty public constructor
    }

    FragmentTenderListBinding binding;
    DownloadManager manager;

    public static TenderList newInstance(String param1, String param2) {
        TenderList fragment = new TenderList();
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

        init();
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTenderListBinding.inflate(inflater, container, false);
        binding.toolbar.setTitle("Tender List");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().onBackPressed();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, new more_frag()).commit();
            }
        });
        return binding.getRoot();
    }

    public void init() {
        TableLayout tableLayout = binding.tenderListTable;

        SimpleApi simpleApi = RetrofitInstance.getClient().create(SimpleApi.class);
        Call<TenderListModalClass> call = simpleApi.tenderList();
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Retrieved Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        call.enqueue(new Callback<TenderListModalClass>() {
            @Override
            public void onResponse(Call<TenderListModalClass> call, Response<TenderListModalClass> response) {
                if (response.isSuccessful()) {

                    int i = 1;
//                    TableLayout.LayoutParams param = new TableLayout.LayoutParams(
//                            TableLayout.LayoutParams.FILL_PARENT,
//                            TableLayout.LayoutParams.WRAP_CONTENT,
//                            1
//
//                    );
                    progressDialog.dismiss();

                    for (TenderListModalClass.Tender tender : response.body().getTenderList()) {
                        TableRow tbrow = new TableRow(getContext());

                        TextView sno = new TextView(getContext());
                        sno.setText("" + i);
                        sno.setGravity(Gravity.CENTER);
                        sno.setLayoutParams(binding.sno.getLayoutParams());
                        tbrow.addView(sno);

                        TextView noOfTender = new TextView(getContext());
                        noOfTender.setText(tender.getNoOfTender());
                        noOfTender.setGravity(Gravity.CENTER);
                        noOfTender.setLayoutParams(binding.sno.getLayoutParams());
                        tbrow.addView(noOfTender);

                        TextView date = new TextView(getContext());
                        date.setText(tender.getDate());
                        date.setGravity(Gravity.CENTER);
                        date.setLayoutParams(binding.sno.getLayoutParams());
                        tbrow.addView(date);

                        Button info = new Button(getContext());
                        info.setText("Download");
                        info.setGravity(Gravity.CENTER);

//                        info.setBackgroundColor(getResources().getColor(R.color.red));
//                        info.setTextColor(getResources().getColor(R.color.white));
                        info.setLayoutParams(binding.sno.getLayoutParams());
                        tbrow.addView(info);

                        info.setOnClickListener(view -> {
//                            manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
//                            Uri uri = Uri.parse(tender.getInfoFile());
//                            DownloadManager.Request request = new DownloadManager.Request(uri);
//                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
//                            manager.enqueue(request);
                            downloadFile(tender.getInfoFile());

                            //Toast.makeText(getContext(), "Downloading...", Toast.LENGTH_SHORT).show();
                        });

                        tableLayout.addView(tbrow);
                        i++;
                    }

                }
            }

            @Override
            public void onFailure(Call<TenderListModalClass> call, Throwable t) {
                progressDialog.dismiss();
                call.cancel();
                throw new RuntimeException("Test Crash for BceClube"); // Force a crash
            }
        });
    }


    void downloadFile(String url){
        File direct = new File(getActivity().getExternalFilesDir(null), "/Tender");
        if(!direct.exists()){
            direct.mkdir();
        }

        DownloadManager mgr = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                .setAllowedOverRoaming(false).setTitle("Tender File")
                .setDescription("Downloading...")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, "/Tender");

        mgr.enqueue(request);

    }
}