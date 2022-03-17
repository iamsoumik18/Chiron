package com.evolver.chiron.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evolver.chiron.databinding.ActivityAdminMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminMainActivity extends AppCompatActivity {

    ActivityAdminMainBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    String adminEmail, adminKey, adminHospital, adminState, adminDistrict, orgKey, hospitalName, bedCnt, price, facilites, Phone, Addres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        adminEmail = intent.getStringExtra("adminEmail");

        getAdminKey();

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar.setVisibility(View.VISIBLE);
                setValue();
                Toast.makeText(getApplicationContext(), "Files Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAdminKey(){
        databaseReference.child("VerifiedAdmins").orderByChild("AdminEmail").equalTo(adminEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot: snapshot.getChildren()){
                    adminKey = childSnapshot.getKey();
                }
                getAdminDetails();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAdminDetails(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminHospital = snapshot.child("VerifiedAdmins").child(adminKey).child("Hospital").getValue().toString();
                adminState = snapshot.child("VerifiedAdmins").child(adminKey).child("State").getValue().toString();
                adminDistrict = snapshot.child("VerifiedAdmins").child(adminKey).child("District").getValue().toString();
                getOrganizationKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getOrganizationKey(){
        databaseReference.child("Organizations").child(adminState).child(adminDistrict).orderByChild("Hospital").equalTo(adminHospital).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot: snapshot.getChildren()){
                    orgKey = childSnapshot.getKey();
                }
                getOrganizationDetails();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getOrganizationDetails(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hospitalName = snapshot.child("Organizations").child(adminState).child(adminDistrict).child(orgKey).child("Hospital").getValue().toString();
                bedCnt = snapshot.child("Organizations").child(adminState).child(adminDistrict).child(orgKey).child("BedCount").getValue().toString();
                price = snapshot.child("Organizations").child(adminState).child(adminDistrict).child(orgKey).child("PricePerBed").getValue().toString();
                Addres = snapshot.child("Organizations").child(adminState).child(adminDistrict).child(orgKey).child("Address").getValue().toString();
                Phone =  snapshot.child("Organizations").child(adminState).child(adminDistrict).child(orgKey).child("PhoneNo").getValue().toString();
                facilites =  snapshot.child("Organizations").child(adminState).child(adminDistrict).child(orgKey).child("Facilities").getValue().toString();
                binding.hospitalName.setText(hospitalName);
                binding.bedCount.setText(bedCnt);
                binding.price.setText(price);
                binding.address.setText(Addres);
                binding.phoneNo.setText(Phone);
                binding.facilities.setText(facilites);
                binding.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setValue(){
        String bedCnt = binding.bedCount.getText().toString();
        String price = binding.price.getText().toString();
        String facilities = binding.facilities.getText().toString();
        String phone = binding.phoneNo.getText().toString();
        String address = binding.address.getText().toString();
        databaseReference.child("Organizations").child(adminState).child(adminDistrict).child(orgKey).child("BedCount").setValue(bedCnt);
        databaseReference.child("Organizations").child(adminState).child(adminDistrict).child(orgKey).child("PricePerBed").setValue(price);
        databaseReference.child("Organizations").child(adminState).child(adminDistrict).child(orgKey).child("Facilities").setValue(facilities);
        databaseReference.child("Organizations").child(adminState).child(adminDistrict).child(orgKey).child("PhoneNo").setValue(phone);
        databaseReference.child("Organizations").child(adminState).child(adminDistrict).child(orgKey).child("Address").setValue(address);
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

}