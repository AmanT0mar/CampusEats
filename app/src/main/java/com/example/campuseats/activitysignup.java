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

import java.util.regex.Pattern;

public class activitysignup extends AppCompatActivity {
    EditText useremail,username,userpwd,confuserpwd,phonenum;
    Button signup;
    TextView alreadyacc;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        useremail = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.user_name);
        userpwd = (EditText) findViewById(R.id.password);
        confuserpwd = (EditText) findViewById(R.id.confirm_password);
        signup = (Button) findViewById(R.id.btnSignUp);
        alreadyacc = (TextView) findViewById(R.id.signin_txtview);
        phonenum = (EditText) findViewById(R.id.phone_no);
        String emailVALID = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        firebaseDatabase = FirebaseDatabase.getInstance("https://campuseats-272f8-default-rtdb.asia-southeast1.firebasedatabase.app");
        user = firebaseDatabase.getReference("User");

        alreadyacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activitysignup.this, activitysignin.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = useremail.getText().toString();
                String Username = username.getText().toString();
                String password = userpwd.getText().toString();
                String Confirmpassword = confuserpwd.getText().toString();
                String phonenumber = phonenum.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(activitysignup.this);
                progressDialog.setMessage("Please wait..!");
                progressDialog.show();

                if (email.length() <= 0 || Username.length() <= 0 || password.length() <= 0 || Confirmpassword.length() <= 0 || phonenumber.length() <= 0){
                    progressDialog.dismiss();
                    Toast.makeText(activitysignup.this,"Field cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else if (!email.matches(emailVALID)){
                    progressDialog.dismiss();
                    Toast.makeText(activitysignup.this,"Enter correct email",Toast.LENGTH_SHORT).show();
                }
                else if (phonenumber.length() != 10){
                    progressDialog.dismiss();
                    Toast.makeText(activitysignup.this,"Enter correct phone number",Toast.LENGTH_SHORT).show();
                }
                else if(!isValidPassword(password)) {
                    progressDialog.dismiss();
                    Toast.makeText(activitysignup.this,"Password doesn't match rules",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!password.equals(Confirmpassword)){
                    progressDialog.dismiss();
                    Toast.makeText(activitysignup.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
                else {
                    user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(phonenumber).exists()) {
                                progressDialog.dismiss();
                                Toast.makeText(activitysignup.this, "User already exists!", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                User userinfo = new User(Username, password, email, phonenumber);
                                user.child(phonenumber).setValue(userinfo);
                                Toast.makeText(activitysignup.this, "SignUp successfully! ", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(activitysignup.this, "Fail to Sign Up" + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
//                Intent intent = new Intent(activitysignup.this,activitysignin.class);
//                startActivity(intent);
            }
        });
    }
    Pattern lowerCase= Pattern.compile("^.*[a-z].*$");
    Pattern upperCase=Pattern.compile("^.*[A-Z].*$");
    Pattern number = Pattern.compile("^.*[0-9].*$");
    Pattern special_Chara = Pattern.compile("^.*[^a-zA-Z0-9].*$");
    private Boolean isValidPassword(String password){
        if(password.length() < 6){
            return false;
        }
        if(!lowerCase.matcher(password).matches()) {
            return false;
        }
        if(!upperCase.matcher(password).matches()) {
            return false;
        }
        if(!number.matcher(password).matches()) {
            return false;
        }
        if(!special_Chara.matcher(password).matches()) {
            return false;
        }
        return true;
    }
}