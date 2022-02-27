package com.evolver.chiron.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evolver.chiron.databinding.ActivityUserSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserSignIn extends AppCompatActivity {

    ActivityUserSignInBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserSignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.progressBar.setVisibility(View.INVISIBLE);

        // click action for Sign In for user if they already have an acccount
        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.email.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Email cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(binding.password.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Password cannot be empty",Toast.LENGTH_SHORT).show();
                }else {
                    String userEmail = binding.email.getText().toString();
                    String userPassword = binding.password.getText().toString();
                    signInWithFirebase(userEmail, userPassword);
                }
            }
        });

        //click action for sign up for user
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserSignIn.this, UserSignUp.class);
                startActivity(i);
            }
        });

        //click action for forget password for user
        binding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserSignIn.this, UserForgetPassword.class);
                startActivity(i);
            }
        });

    }

    // Action for stay on the sign in page after close the app
    protected  void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            Intent i = new Intent(UserSignIn.this, UserMainActivity.class);
            startActivity(i);
            finish();
        }
    }

    //sign in method that connect firebase
    public void signInWithFirebase(String userEmail, String userPassword){
        binding.progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(UserSignIn.this, UserMainActivity.class);
                    startActivity(i);
                    Toast.makeText(UserSignIn.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(UserSignIn.this, "Email ID or Password is incorrect", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.email.setText(null);
                    binding.password.setText(null);
                }
            }
        });
    }

}