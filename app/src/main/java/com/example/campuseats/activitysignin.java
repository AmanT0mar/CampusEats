package com.example.campuseats;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campuseats.viewmodel.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activitysignin extends AppCompatActivity {
    EditText phonenum,password;
    Button signinbtn;
    TextView noacc;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        phonenum = findViewById(R.id.phone_no);
        password = findViewById(R.id.user_password);
        signinbtn = findViewById(R.id.btnSignIn);
        noacc = findViewById(R.id.signup_txtview);

        firebaseDatabase = FirebaseDatabase.getInstance("https://campuseats-272f8-default-rtdb.asia-southeast1.firebasedatabase.app");
        user = firebaseDatabase.getReference("User");

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(activitysignin.this);
                progressDialog.setMessage("Please wait!");
                progressDialog.show();
                user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(phonenum.getText().toString()).exists()) {
                            progressDialog.dismiss();
                            User user = snapshot.child(phonenum.getText().toString()).getValue(User.class);
                            user.setPhone(phonenum.getText().toString());
                            if (user.getPassword().equals(password.getText().toString())) {
                                Intent home = new Intent(activitysignin.this,activityhome.class);
                                home.putExtra("phonenum",phonenum.getText().toString());
                                startActivity(home);
                                finish();
                            } else {
                                Toast.makeText(activitysignin.this, "Phone no. or Password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(activitysignin.this, "Please Sign Up First", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        noacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activitysignin.this, activitysignup.class);
                startActivity(intent);
            }
        });
    }
}