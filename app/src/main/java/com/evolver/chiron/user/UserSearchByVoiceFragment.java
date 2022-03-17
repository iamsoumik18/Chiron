package com.evolver.chiron.user;

import android.content.Intent;
import android.os.Bundle;
import static android.app.Activity.RESULT_OK;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.load.model.Model;
import com.evolver.chiron.databinding.FragmentUserSearchByVoiceBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class UserSearchByVoiceFragment extends Fragment {

    FragmentUserSearchByVoiceBinding binding;
    UserAdapter adapter;

    static final int REQUEST_CODE_SPEECH_INPUT = 1;
    static final int REQUEST_CODE_SPEECH_INPUT2= 2;

    String selectedState;
    String selectedDistrict;

    private int cnt;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public UserSearchByVoiceFragment() {

    }

    public static UserSearchByVoiceFragment newInstance(String param1, String param2) {
        UserSearchByVoiceFragment fragment = new UserSearchByVoiceFragment();
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
        binding = FragmentUserSearchByVoiceBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.stateVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speechRecognition1();
            }
        });

        binding.districtVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speechRecognition2();
            }
        });

        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedState==null) {
                    Toast.makeText(getActivity(), "Please say your State", Toast.LENGTH_LONG).show();
                    binding.tvIndianStatesHead.setError("State is required!");
                }else if(selectedDistrict==null) {
                    Toast.makeText(getActivity(), "Please say your District", Toast.LENGTH_LONG).show();
                    binding.tvIndianDistrictsHead.setError("District is required!");
                    binding.tvIndianStatesHead.setError(null);
                }else {
                    binding.tvIndianStatesHead.setError(null);
                    binding.tvIndianDistrictsHead.setError(null);
                    binding.progressBarContainer.setVisibility(View.VISIBLE);
                    cnt = 0;
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Organizations").child(selectedState).child(selectedDistrict);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot childSnapshot: snapshot.getChildren()){
                                cnt++;
                            }
                            if(cnt!=0) {
                                binding.detailsContainer.setVisibility(View.VISIBLE);
                                binding.recView.setLayoutManager(new LinearLayoutManager(getContext()));
                                FirebaseRecyclerOptions<UserModel> options =
                                        new FirebaseRecyclerOptions.Builder<UserModel>()
                                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Organizations").child(selectedState).child(selectedDistrict).orderByChild("Hospital"),UserModel.class)
                                                .build();
                                adapter = new UserAdapter(options);
                                adapter.startListening();
                                binding.recView.setAdapter(adapter);
                            }else{
                                binding.noResultContainer.setVisibility(View.VISIBLE);
                                binding.detailsContainer.setVisibility(View.GONE);
                                binding.progressBarContainer.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    public void speechRecognition1(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speech To Text");
        try {
            startActivityForResult(i, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e){ }
    }

    public void speechRecognition2(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speech To Text");
        try {
            startActivityForResult(i, REQUEST_CODE_SPEECH_INPUT2);
        }
        catch (Exception e){ }
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                selectedState = Camelcase(Objects.requireNonNull(result).get(0));
                binding.tvSelectedState.setText(selectedState);
            }
        } else if (requestCode == REQUEST_CODE_SPEECH_INPUT2) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                selectedDistrict = Camelcase(Objects.requireNonNull(result).get(0));
                binding.tvSelectedDistrict.setText(selectedDistrict);
            }
        }
    }

    public String Camelcase(String s) {
        if (s == null)
            return null;
        final StringBuilder ret = new StringBuilder(s.length());
        for (final String word : s.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(Character.toUpperCase(word.charAt(0)));
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length() == s.length()))
                ret.append(" ");
        }
        return ret.toString();
    }

}