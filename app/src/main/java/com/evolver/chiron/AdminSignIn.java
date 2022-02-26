package com.evolver.chiron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evolver.chiron.databinding.ActivityAdminSignInBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminSignIn extends AppCompatActivity {

    ActivityAdminSignInBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("VerifiedAdmin");

    FirebaseDatabase database1 = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference1 = database1.getReference();

    String a;
    String b;

    String VerEmail1;
    String verpassword1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminSignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.progressBar.setVisibility(View.INVISIBLE);


        //sign in as admin
        binding.btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminCheck();
                a = binding.email.getText().toString();
                b = binding.password.getText().toString();
                if(a.equals(VerEmail1) && b.equals(verpassword1)){
                    detailOfAdmin();
                    Intent i = new Intent(AdminSignIn.this, AdminMainActivity.class);
                    i.putExtra("adminEmail",a);
                    startActivity(i);
                }

            }
        });
        binding.requestAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminSignIn.this, AdminRequestActivity.class);
                startActivity(i);
            }
        });
    }

    public void AdminCheck(){

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                VerEmail1 = dataSnapshot.child("1").child("AdminEmail").getValue().toString();
                verpassword1 = dataSnapshot.child(String.valueOf("1")).child("AdminPassword").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                Toast.makeText(AdminSignIn.this, "Their is some error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void detailOfAdmin(){

        String a = binding.email.getText().toString();
        databaseReference1.child("Admin Login Details").setValue(a);


    }

}