package com.evolver.chiron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evolver.chiron.databinding.ActivityMainLoginBinding;

public class MainLogin extends AppCompatActivity {

    ActivityMainLoginBinding  binding;

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
                Toast.makeText(getApplicationContext(),"Admin",Toast.LENGTH_SHORT).show();
            }
        });

        //Click Action for User Button
        binding.userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainLogin.this, SignUpUser.class);
                startActivity(i);
            }
        });


        //Click Action for Request Button
        binding.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Guest",Toast.LENGTH_SHORT).show();
            }
        });


        //Click Action for About Button
        binding.aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainLogin.this, SignUpAdmin.class);
                startActivity(i);
            }
        });

    }
}