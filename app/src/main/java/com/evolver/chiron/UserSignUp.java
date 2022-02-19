package com.evolver.chiron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserSignUp extends AppCompatActivity {

    EditText Email;
    EditText pass;
    Button Lg;
    ProgressBar p;
    FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        Email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        Lg = findViewById(R.id.btlogin);
        p = findViewById(R.id.pp);

        p.setVisibility(View.INVISIBLE);

        //click action for sign up for user if they dont have account
        Lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Email.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Email cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(pass.getText().toString().contentEquals("")){
                    Toast.makeText(getApplicationContext(),"Password cannot be empty",Toast.LENGTH_SHORT).show();
                }else {
                    String userEmail = Email.getText().toString();
                    String userPassword = pass.getText().toString();
                    singUpFirebase(userEmail, userPassword);
                }
            }
        });
    }

    //Sign up method that connect the user to the firebase
    public void singUpFirebase(String userEmail, String userPassword) {
        p.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UserSignUp.this, "Your Account is created Successfully", Toast.LENGTH_LONG).show();
                    finish();
                    p.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(UserSignUp.this, "There is an error. Please Try again Later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}