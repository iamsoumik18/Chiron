package com.evolver.chiron.guest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evolver.chiron.databinding.FragmentGuestSearchByVoiceBinding;

public class GuestSearchByVoiceFragment extends Fragment {

    FragmentGuestSearchByVoiceBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GuestSearchByVoiceFragment() {

    }

    public static GuestSearchByVoiceFragment newInstance(String param1, String param2) {
        GuestSearchByVoiceFragment fragment = new GuestSearchByVoiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGuestSearchByVoiceBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}