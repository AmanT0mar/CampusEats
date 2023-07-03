package com.example.campuseats;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<FoodItem> list;
    OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(Context context, ArrayList<FoodItem> list, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_list_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem foodItem = list.get(position);
        String l = foodItem.getId();
        holder.foodname.setText(foodItem.getName());
        holder.foodprice.setText("₹ "+foodItem.getPrice());
        holder.foodrating.setText(foodItem.getRating()+" ☆");
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("foodimages/"+foodItem.getImageURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
        holder.foodimg.setImageBitmap(myBitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    FoodItem item = list.get(position);
                    onItemClickListener.onItemClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
//    public void setOnClickListener(OnClickListener onClickListener) {
//        this.onClickListener = onClickListener;
//    }
    public interface OnItemClickListener {
        void onItemClick(FoodItem foodItem);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodname,foodprice,foodrating;
        ImageView foodimg;

        ViewHolder(View itemView) {
            super(itemView);
            foodname = itemView.findViewById(R.id.food_name_home);
            foodprice = itemView.findViewById(R.id.food_price_home);
            foodrating = itemView.findViewById(R.id.food_rating_home);
            foodimg = itemView.findViewById(R.id.food_img_home);
        }
    }
}