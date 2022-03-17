package com.evolver.chiron.admin;

import androidx.annotation.NonNull;
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
    DatabaseReference databaseReference = database.getReference();

    private String checkId, curEmail, curPassword, verEmail, verPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminSignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.progressBar.setVisibility(View.INVISIBLE);

        //sign in as admin
        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar.setVisibility(View.VISIBLE);
                curEmail = binding.email.getText().toString();
                curPassword = binding.password.getText().toString();
                AdminCheck();
            }
        });

        binding.resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSignIn.this, AdminForgetPassword.class);
                startActivity(intent);
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

    private void AdminCheck(){
        databaseReference.child("VerifiedAdmins").orderByChild("AdminEmail").equalTo(curEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot: snapshot.getChildren()){
                    checkId = childSnapshot.getKey();
                }
                if(checkId==null){
                    Toast.makeText(getApplicationContext(), "Email ID or Password is Incorrect", Toast.LENGTH_SHORT).show();
                    binding.email.setText(null);
                    binding.password.setText(null);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            verPassword = snapshot.child("VerifiedAdmins").child(checkId).child("AdminPassword").getValue().toString();
                            if (verPassword.equals(curPassword)) {
                                Intent intent = new Intent(AdminSignIn.this, AdminMainActivity.class);
                                intent.putExtra("adminEmail", curEmail);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Email ID or Password is Incorrect", Toast.LENGTH_SHORT).show();
                                binding.email.setText(null);
                                binding.password.setText(null);
                                binding.progressBar.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}