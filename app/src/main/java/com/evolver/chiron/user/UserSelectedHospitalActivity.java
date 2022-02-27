package com.evolver.chiron.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.evolver.chiron.databinding.ActivityUserSelectedHospitalBinding;

public class UserSelectedHospitalActivity extends AppCompatActivity {

    ActivityUserSelectedHospitalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserSelectedHospitalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        binding.hospitalName.setText(intent.getStringExtra("name"));
        binding.bedCnt.setText(intent.getStringExtra("bedCnt"));
        binding.price.setText(intent.getStringExtra("price"));
    }
}