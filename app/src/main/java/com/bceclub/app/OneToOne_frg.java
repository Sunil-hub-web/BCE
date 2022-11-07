package com.bceclub.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bceclub.app.Adapters.OneToOneAdapter;
import com.bceclub.app.Models.OneToOne_Model;
import com.bceclub.app.databinding.OnetoonefragBinding;

import java.util.ArrayList;

public class OneToOne_frg extends Fragment {

    OnetoonefragBinding binding;
    OneToOneAdapter oneToOneAdapter;
    ArrayList<OneToOne_Model> oneToOne_models;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = OnetoonefragBinding.inflate(getLayoutInflater(),container,false);
        View view = binding.getRoot();

        binding.btnSendonetonne.setOnClickListener(view1 ->
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new member_frag()).addToBackStack(null).commit());

        return view;
    }
}
