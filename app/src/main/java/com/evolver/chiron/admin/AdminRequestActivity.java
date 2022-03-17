package com.evolver.chiron.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evolver.chiron.MainLogin;
import com.evolver.chiron.databinding.ActivityAdminRequestBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRequestActivity extends AppCompatActivity {

    ActivityAdminRequestBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminRequestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.progressBar.setVisibility(View.INVISIBLE);

        binding.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.orgName.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Name cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(binding.orgEmail.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Email cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(binding.orgPhoneNo.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Phone number cannon be empty",Toast.LENGTH_SHORT).show();
                }else if(binding.orgAddress.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Address cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(binding.orgDistrict.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"District cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(binding.orgState.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"State cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(binding.orgCountry.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Country cannot be empty",Toast.LENGTH_SHORT).show();
                }else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    sendAdminRequestDetails();
                    Toast.makeText(getApplicationContext(), "Request Submitted. We will reach you as soon as possible. Thank You!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(AdminRequestActivity.this, MainLogin.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }
    public void sendAdminRequestDetails(){

        String name = binding.orgName.getText().toString();
        String email = binding.orgEmail.getText().toString();
        String phn = binding.orgPhoneNo.getText().toString();
        String address = binding.orgAddress.getText().toString();
        String dist = binding.orgDistrict.getText().toString();
        String state = binding.orgState.getText().toString();
        String country = binding.orgCountry.getText().toString();

        databaseReference.child("AdminRequests").child(country).child(state).child(dist).child("OrganizationName").setValue(name);
        databaseReference.child("AdminRequests").child(country).child(state).child(dist).child("OrganizationEmail").setValue(email);
        databaseReference.child("AdminRequests").child(country).child(state).child(dist).child("OrganizationPhoneNo").setValue(phn);
        databaseReference.child("AdminRequests").child(country).child(state).child(dist).child("OrganizationAddress").setValue(address);

    }
}