package com.evolver.chiron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evolver.chiron.databinding.ActivityMainLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainLogin extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    ActivityMainLoginBinding  binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("User Detail");
    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference2 = database2.getReference().child("VerifiedAdmin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Click Action for Admin Button
        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainLogin.this, AdminSignIn.class);
                startActivity(i);
            }
        });

        //Click Action for User Button
        binding.userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainLogin.this, UserSignIn.class);
                startActivity(i);
            }
        });

        //Click Action for Guest Button
        binding.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainLogin.this,GuestMainActivity.class);
                startActivity(i);
            }
        });

        //Click Action for About Button
        binding.aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"About",Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*
    This is a temporary fix where MainLogin Activity appears on backspace
    again when a user sign out of the app.
    */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userId = auth.getUid();
                    int val = Integer.valueOf(snapshot.child(userId).child("flag").getValue().toString());
                    if(val==0){
                        Intent i = new Intent(MainLogin.this, UserMainActivity.class);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(MainLogin.this, AdminMainActivity.class);
                        startActivity(i);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Intent i = new Intent(MainLogin.this, AdminMainActivity.class);
                    startActivity(i);
                }
            });
        }
    }
*/
}