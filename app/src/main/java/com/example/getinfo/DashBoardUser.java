package com.example.getinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class DashBoardUser extends AppCompatActivity {

    Button bt;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_user);

        bt = findViewById(R.id.bt1);

        //click action for sign out
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent i = new Intent(DashBoardUser
                        .this, SignUpUser.class);
                startActivity(i);
                finish();
            }
        });
    }
}