package com.evolver.chiron.admin;

import androidx.annotation.Discouraged;
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
        databaseReference.child("VerifiedAdmin").orderByChild("AdminEmail").equalTo(adminEmail).addListenerForSingleValueEvent(new ValueEventListener() {
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
                adminHospital = snapshot.child("VerifiedAdmin").child(adminKey).child("hospital").getValue().toString();
                adminState = snapshot.child("VerifiedAdmin").child(adminKey).child("State").getValue().toString();
                adminDistrict = snapshot.child("VerifiedAdmin").child(adminKey).child("District").getValue().toString();
                getOrganizationKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getOrganizationKey(){
        databaseReference.child("Organization").child(adminState).child(adminDistrict).orderByChild("hospital").equalTo(adminHospital).addListenerForSingleValueEvent(new ValueEventListener() {
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
                hospitalName = snapshot.child("Organization").child(adminState).child(adminDistrict).child(orgKey).child("hospital").getValue().toString();
                bedCnt = snapshot.child("Organization").child(adminState).child(adminDistrict).child(orgKey).child("bed").getValue().toString();
                price = snapshot.child("Organization").child(adminState).child(adminDistrict).child(orgKey).child("price").getValue().toString();
                Addres = snapshot.child("Organization").child(adminState).child(adminDistrict).child(orgKey).child("Addres").getValue().toString();
                Phone =  snapshot.child("Organization").child(adminState).child(adminDistrict).child(orgKey).child("Phone").getValue().toString();
                facilites =  snapshot.child("Organization").child(adminState).child(adminDistrict).child(orgKey).child("facilites").getValue().toString();
                binding.hospitalName.setText(hospitalName);
                binding.bedCount.setText(bedCnt);
                binding.price.setText(price);
                binding.Addres.setText(Addres);
                binding.Phone.setText(Phone);
                binding.facilites.setText(facilites);
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
        String facilites = binding.facilites.getText().toString();
        String Phone = binding.Phone.getText().toString();
        String Addres = binding.Addres.getText().toString();
        databaseReference.child("Organization").child(adminState).child(adminDistrict).child(orgKey).child("bed").setValue(bedCnt);
        databaseReference.child("Organization").child(adminState).child(adminDistrict).child(orgKey).child("price").setValue(price);
        databaseReference.child("Organization").child(adminState).child(adminDistrict).child(orgKey).child("facilites").setValue(facilites);
        databaseReference.child("Organization").child(adminState).child(adminDistrict).child(orgKey).child("Phone").setValue(Phone);
        databaseReference.child("Organization").child(adminState).child(adminDistrict).child(orgKey).child("Addres").setValue(Addres);
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

}