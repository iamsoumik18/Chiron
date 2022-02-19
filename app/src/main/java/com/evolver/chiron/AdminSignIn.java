package com.evolver.chiron;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.evolver.chiron.databinding.ActivityAdminSignInBinding;

public class AdminSignIn extends AppCompatActivity {

    ActivityAdminSignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminSignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.progressBar.setVisibility(View.INVISIBLE);
    }
}