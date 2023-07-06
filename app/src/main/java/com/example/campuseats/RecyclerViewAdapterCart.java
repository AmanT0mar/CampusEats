package com.example.campuseats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuseats.viewmodel.CartItems;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewAdapterCart extends RecyclerView.Adapter<RecyclerViewAdapterCart.ViewHolder> {
    Context context;
    ArrayList<CartItems> list;

    DatabaseReference db = FirebaseDatabase.getInstance("https://campuseats-272f8-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Cart");

    public RecyclerViewAdapterCart(Context context, ArrayList<CartItems> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_cart_food_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItems cartItems = list.get(position);
        holder.foodname.setText(cartItems.getName());
        holder.foodprice.setText("₹ "+cartItems.getPrice());
        holder.foodquantity.setText("Nos "+cartItems.getQuantity());
        int tot = Integer.parseInt(cartItems.getPrice()) * Integer.parseInt(cartItems.getQuantity());
        holder.foodtotal.setText("₹ "+ tot);
        holder.removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.child(cartItems.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,getItemCount());
                        CartFragment.ChangeAmount(Integer.parseInt(cartItems.getPrice())*Integer.parseInt(cartItems.getQuantity()));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView foodname,foodprice,foodquantity,foodtotal;
        ImageButton removebtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodname = itemView.findViewById(R.id.food_name);
            foodprice = itemView.findViewById(R.id.food_price);
            foodquantity = itemView.findViewById(R.id.food_quantity);
            foodtotal = itemView.findViewById(R.id.total_price_item);
            removebtn = itemView.findViewById(R.id.remove_cart_item);
        }
    }
}
