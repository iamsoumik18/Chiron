package com.evolver.chiron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evolver.chiron.databinding.ActivityAdminRequestBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRequestActivity extends AppCompatActivity {

    ActivityAdminRequestBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    FirebaseAuth authm = FirebaseAuth.getInstance();
    FirebaseUser user = authm.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminRequestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btRequest.setClickable(false);
                detailOfuser();
                Toast.makeText(AdminRequestActivity.this, "Request Submitted. We will reach you as soon as possible. Thank You!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(AdminRequestActivity.this, MainLogin.class);
                startActivity(i);
                finish();
            }
        });

    }
    public void detailOfuser(){
        String name = binding.edname.getText().toString();
        String email = binding.edemail.getText().toString();
        String phn = binding.edph.getText().toString();
        String addr = binding.edaddress.getText().toString();
        String dist = binding.eddist.getText().toString();
        String state = binding.edstate.getText().toString();
        String country = binding.edcountry.getText().toString();

        databaseReference.child("Admin Request").child(country).child(state).child(dist).child("Organization Name").setValue(name);
        databaseReference.child("Admin Request").child(country).child(state).child(dist).child("Organization Email").setValue(email);
        databaseReference.child("Admin Request").child(country).child(state).child(dist).child("Organization Phone no").setValue(phn);
        databaseReference.child("Admin Request").child(country).child(state).child(dist).child("Organization Address").setValue(addr);

    }
}