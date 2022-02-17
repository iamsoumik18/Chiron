package com.example.getinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpUser extends AppCompatActivity {

    EditText Email;
    EditText pass;
    Button Lg;
    TextView SgUp;
    TextView Forg;
    ProgressBar p ;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

        Email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        Lg = findViewById(R.id.btlogin);
        SgUp = findViewById(R.id.Signupbt);
        Forg = findViewById(R.id.Forgetbt);
        p = findViewById(R.id.pp);

        p.setVisibility(View.INVISIBLE);

        // click action for Sign In for user if they already have an acccount
        Lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UEmail = Email.getText().toString();
                String Upassword = pass.getText().toString();
                SignWithFirebase(UEmail, Upassword);

            }
        });
        //click action for sign up for user
        SgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SignUpUser.this, UserLogIn.class);
                startActivity(i);
            }
        });
        //click action for forgrt password for user
        Forg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpUser.this, ForgetPassword.class);
                startActivity(i);
            }
        });

    }
    // Action for stay on the sign in page after close the app
    protected  void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            Intent i = new Intent(SignUpUser.this, DashBoardUser.class);
            startActivity(i);
            finish();
        }
    }
    //sign in method that connect firebase
    public void SignWithFirebase(String UserMail, String UsserPassword){
        p.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(UserMail, UsserPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(SignUpUser.this, DashBoardUser.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(SignUpUser.this, "Succesfully Sign in", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignUpUser.this, "There is a Problem . Please Try Agaig Later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}