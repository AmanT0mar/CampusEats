package com.example.campuseats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activitywelcome extends AppCompatActivity {

    Button signupbtn,signinbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        signupbtn = (Button) findViewById(R.id.signup_btn);
        signinbtn = (Button) findViewById(R.id.signin_btn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activitywelcome.this, activitysignup.class);
                startActivity(intent);

            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activitywelcome.this, activitysignin.class);
                startActivity(intent);

            }
        });
    }

}