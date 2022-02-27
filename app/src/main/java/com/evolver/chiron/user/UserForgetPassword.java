package com.evolver.chiron.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evolver.chiron.MainLogin;
import com.evolver.chiron.databinding.ActivityUserForgetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class UserForgetPassword extends AppCompatActivity {

    ActivityUserForgetPasswordBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserForgetPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.progressBar.setVisibility(View.INVISIBLE);

        //click action for the reset password
        binding.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.email.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Email cannot be empty",Toast.LENGTH_SHORT).show();
                }else {
                    String userEmail = binding.email.getText().toString();
                    forgetPassword(userEmail);
                }
            }
        });
    }

    //forget password method that sent an email to the user and user can reset their password
    private void forgetPassword(String userEmail){
        binding.progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserForgetPassword.this, "New password has been successfully sent to your registered email", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UserForgetPassword.this, MainLogin.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(UserForgetPassword.this, "There is a problem. Please try again later", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}