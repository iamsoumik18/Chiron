package com.evolver.chiron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evolver.chiron.admin.AdminSignIn;
import com.evolver.chiron.databinding.ActivityMainLoginBinding;
import com.evolver.chiron.guest.GuestMainActivity;
import com.evolver.chiron.user.UserMainActivity;
import com.evolver.chiron.user.UserSignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainLogin extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    ActivityMainLoginBinding binding;



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
        binding.guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainLogin.this, GuestMainActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(MainLogin.this, UserMainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}