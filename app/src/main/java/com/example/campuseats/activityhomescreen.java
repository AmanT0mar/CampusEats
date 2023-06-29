package com.example.campuseats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class activityhomescreen extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<FoodItem> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        recyclerView = findViewById(R.id.food_items);


        String phonenum = getIntent().getStringExtra("phonenum");

        databaseReference = FirebaseDatabase.getInstance("https://campuseats-272f8-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("FoodItems");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(this,list);
        recyclerView.setAdapter(recyclerViewAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    FoodItem foodItem = dataSnapshot.getValue(FoodItem.class);
                    list.add(foodItem);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activityhomescreen.this, "BLAH", Toast.LENGTH_LONG).show();
            }
        });
    }
}