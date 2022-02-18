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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    EditText Email;
    Button Reset;

    ProgressBar p;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Email = findViewById(R.id.ed1);
        Reset = findViewById(R.id.re1);
        p = findViewById(R.id.pp);

        p.setVisibility(View.INVISIBLE);

        //click action for the reset password
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.setVisibility(View.VISIBLE);

                String ukEmail = Email.getText().toString();
                forgetpassword(ukEmail);
            }
        });
    }
    //forget password method that sent an email to the user and user can reset their password
    public void forgetpassword(String UserMail){
        p.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(UserMail).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "Sent The reset password link to your registered email", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ForgetPassword.this, "Their is a Problem. Try Again Later", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}