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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignUp extends AppCompatActivity {

    EditText Email;
    EditText pass;
    Button Lg;
    ProgressBar p;
    EditText Adress;
    EditText np;
    EditText phn;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    int Q=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        Email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        Lg = findViewById(R.id.btlogin);
        p = findViewById(R.id.pp);
        Adress = findViewById(R.id.address);
        np =findViewById(R.id.name);
        phn = findViewById(R.id.phNo);



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
                    detail();
                    Toast.makeText(UserSignUp.this, "Your Account is created Successfully", Toast.LENGTH_LONG).show();
                    finish();
                    p.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(UserSignUp.this, "There is an error. Please Try again Later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void detail(){
        String em = Email.getText().toString();
        String ps = pass.getText().toString();
        String ad = Adress.getText().toString();
        String nm = np.getText().toString();
        String pp = phn.getText().toString();
        String userId = auth.getUid();


        databaseReference.child("User Detail").child(userId).child("EMAIL").setValue(em);
        databaseReference.child("User Detail").child(userId).child("password").setValue(ps);
        databaseReference.child("User Detail").child(userId).child("adress").setValue(ad);
        databaseReference.child("User Detail").child(userId).child("name").setValue(nm);
        databaseReference.child("User Detail").child(userId).child("phone no").setValue(pp);
        databaseReference.child("User Detail").child(userId).child("flag").setValue(0);


    }

}