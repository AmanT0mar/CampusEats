package com.example.campuseats;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campuseats.viewmodel.CartItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;

public class activityfooddes extends AppCompatActivity {

    TextView foodname,foodprice,foodrating;
    ImageView foodimg;
    ImageButton incbtn,debtn;
    Button addtocart;
    TextView foodquantity;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference cart;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_des);

        FoodItem foodItem = (FoodItem) getIntent().getSerializableExtra("fooditem");
        foodname = findViewById(R.id.food_name_);
        foodprice = findViewById(R.id.food_price_);
        foodrating = findViewById(R.id.food_rating_);
        foodimg = findViewById(R.id.food_image_);
        addtocart = findViewById(R.id.add_to_cart_);
        incbtn = findViewById(R.id.food_increment_);
        debtn = findViewById(R.id.food_decrement_);
        foodquantity = findViewById(R.id.food_quantity_);
        firebaseDatabase = FirebaseDatabase.getInstance("https://campuseats-272f8-default-rtdb.asia-southeast1.firebasedatabase.app");
        cart = firebaseDatabase.getReference("Cart");

        foodname.setText(foodItem.getName());
        foodprice.setText("₹ "+foodItem.getPrice());
        foodrating.setText(foodItem.getRating()+" ☆");
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("foodimages/"+foodItem.getImageURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
        foodimg.setImageBitmap(myBitmap);

        incbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                foodquantity.setText(""+i);
            }
        });
        debtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 0){
                    foodquantity.setText(""+i);
                }
                else {
                    i--;
                    foodquantity.setText("" + i);
                }
            }
        });
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = foodItem.getId();
                String fname = foodItem.getName();
                String fprice = foodItem.getPrice();
                String fquantity = foodquantity.getText().toString();


                if (fquantity.equals("0")){
                    Toast.makeText(activityfooddes.this, "Invalid Quantity", Toast.LENGTH_SHORT).show();
                }
                else{
                    cart.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(id).exists()){
                                Toast.makeText(activityfooddes.this, "Item already in Cart", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                CartItem cartItem = new CartItem(id,fname,fprice,fquantity);
                                cart.child(id).setValue(cartItem);
                                Toast.makeText(activityfooddes.this, "Item added to cart", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(activityfooddes.this, "Failed to add item to cart" + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
}
