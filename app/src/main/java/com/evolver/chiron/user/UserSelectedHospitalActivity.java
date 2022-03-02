package com.evolver.chiron.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.evolver.chiron.databinding.ActivityUserSelectedHospitalBinding;

public class UserSelectedHospitalActivity extends AppCompatActivity {

    ActivityUserSelectedHospitalBinding binding;
    private String hospitalName, hospitalAddress, hospitalContact, hospitalFacilities, hospitalBedCnt, hospitalBedPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserSelectedHospitalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        hospitalName = intent.getStringExtra("name");
        hospitalAddress = intent.getStringExtra("address");
        hospitalContact = intent.getStringExtra("phoneNo");
        hospitalFacilities = intent.getStringExtra("facilities");
        hospitalBedCnt = intent.getStringExtra("bedCnt");
        hospitalBedPrice = intent.getStringExtra("price");

        binding.hospitalName.setText(hospitalName);
        binding.address.setText(hospitalAddress);
        binding.phoneNo.setText(hospitalContact);
        binding.facilities.setText(hospitalFacilities);
        binding.bedCnt.setText(hospitalBedCnt);
        binding.price.setText("Rs " + hospitalBedPrice);

    }
}