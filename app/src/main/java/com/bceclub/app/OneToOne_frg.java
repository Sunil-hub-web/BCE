package com.bceclub.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bceclub.app.databinding.OnetoonefragBinding;

public class OneToOne_frg extends Fragment {

    OnetoonefragBinding binding;

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
