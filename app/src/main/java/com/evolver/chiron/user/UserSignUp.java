package com.evolver.chiron.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evolver.chiron.MainLogin;
import com.evolver.chiron.databinding.ActivityUserSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignUp extends AppCompatActivity {

    ActivityUserSignUpBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserSignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.progressBar.setVisibility(View.INVISIBLE);

        //click action for sign up for user if they dont have account
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.name.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Name cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(binding.email.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Email cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(binding.password.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Password cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(binding.address.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Address cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(binding.phoneNo.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Phone Number cannot be empty",Toast.LENGTH_SHORT).show();
                }else{
                    String userEmail = binding.email.getText().toString();
                    String userPassword = binding.password.getText().toString();
                    singUpWithFirebase(userEmail, userPassword);
                }
            }
        });
    }

    //Sign up method that connect the user to the firebase
    private void singUpWithFirebase(String userEmail, String userPassword) {
        binding.progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    addUserDetails();
                    Toast.makeText(UserSignUp.this, "Your Account is created successfully", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(UserSignUp.this, MainLogin.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UserSignUp.this, "There is an error. Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addUserDetails(){
        String name = binding.name.getText().toString();
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();
        String address = binding.address.getText().toString();
        String phoneNo = binding.phoneNo.getText().toString();
        String userId = auth.getUid();

        databaseReference.child("UserDetails").child(userId).child("Name").setValue(name);
        databaseReference.child("UserDetails").child(userId).child("Email").setValue(email);
        databaseReference.child("UserDetails").child(userId).child("Password").setValue(password);
        databaseReference.child("UserDetails").child(userId).child("Address").setValue(address);
        databaseReference.child("UserDetails").child(userId).child("PhoneNo").setValue(phoneNo);
    }

}