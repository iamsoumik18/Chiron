package com.evolver.chiron.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evolver.chiron.MainLogin;
import com.evolver.chiron.databinding.ActivityAdminForgetPasswordBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminForgetPassword extends AppCompatActivity {

    ActivityAdminForgetPasswordBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    private String curEmail, curPassword, newPassword, adminId, verPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminForgetPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.progressBar.setVisibility(View.INVISIBLE);

        binding.btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curEmail = binding.email.getText().toString();
                curPassword = binding.curPassword.getText().toString();
                newPassword = binding.newPassword.getText().toString();
                binding.progressBar.setVisibility(View.VISIBLE);
                resetPassword();
            }
        });

    }

    private void resetPassword(){
        databaseReference.child("VerifiedAdmin").orderByChild("AdminEmail").equalTo(curEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot: snapshot.getChildren()){
                    adminId = childSnapshot.getKey();
                }
                if(adminId==null){
                    //Admin doest exist
                    Toast.makeText(getApplicationContext(),"Admin doesn't Exist",Toast.LENGTH_LONG).show();
                    binding.email.setText(null);
                    binding.curPassword.setText(null);
                    binding.newPassword.setText(null);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }else{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            verPassword = snapshot.child("VerifiedAdmin").child(adminId).child("AdminPassword").getValue().toString();
                            if(verPassword.equals(curPassword)){
                                databaseReference.child("VerifiedAdmin").child(adminId).child("AdminPassword").setValue(newPassword);
                                Toast.makeText(getApplicationContext(),"Password Reset Successfully",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AdminForgetPassword.this, MainLogin.class);
                                startActivity(intent);
                                finish();
                            }else{
                                //Current password doest match
                                Toast.makeText(getApplicationContext(),"Current Password doesn't match",Toast.LENGTH_LONG).show();
                                binding.email.setText(null);
                                binding.curPassword.setText(null);
                                binding.newPassword.setText(null);
                                binding.progressBar.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}