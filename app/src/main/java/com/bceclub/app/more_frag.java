package com.bceclub.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bceclub.SessionManager;
import com.bceclub.app.databinding.FragmentMoreFragBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link more_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class more_frag extends Fragment {
    FragmentMoreFragBinding binding;

    public more_frag() {
        // Required empty public constructor
    }

    public static more_frag newInstance(String param1, String param2) {
        more_frag fragment = new more_frag();
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
        binding.tenderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_more_frag_to_tenderList);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new TenderList()).commit();
            }
        });

        binding.helpDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_more_frag_to_helpDesk);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HelpDesk()).commit();
            }
        });

        binding.memberAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_more_frag_to_memberAsk2);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new memberAsk()).commit();

            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SessionManager sessionManager = new SessionManager(getActivity());
                sessionManager.logoutUser();

            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMoreFragBinding.inflate(inflater, container, false);
        binding.toolbar.setTitle("More");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().onBackPressed();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,new home_frag()).commit();
            }
        });
        return binding.getRoot();
    }
}