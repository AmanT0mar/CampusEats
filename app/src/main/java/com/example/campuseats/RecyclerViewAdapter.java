package com.example.campuseats;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<FoodItem> list;

    public RecyclerViewAdapter(Context context, ArrayList<FoodItem> list) {
        this.context = context;
        this.list = list;
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
        holder.txtview1.setText(foodItem.getName());
        holder.txtview2.setText("₹ "+foodItem.getPrice());
        holder.txtview3.setText(foodItem.getRating()+" ☆");
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("foodimages/"+foodItem.getImageURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
        holder.imgview.setImageBitmap(myBitmap);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtview1,txtview2,txtview3;
        ImageView imgview;

        ViewHolder(View itemView) {
            super(itemView);
            txtview1 = itemView.findViewById(R.id.textView1);
            txtview2 = itemView.findViewById(R.id.textView2);
            txtview3 = itemView.findViewById(R.id.textView3);
            imgview = itemView.findViewById(R.id.imageview1);
        }
    }
}