package com.example.moriah.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;


public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
 public TextView txtmenuname;
 public ImageView imageView;
 CardView cvMenuItem;

 private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        txtmenuname=itemView.findViewById(R.id.menu_name);
        imageView=itemView.findViewById(R.id.menu_image);
        cvMenuItem = itemView.findViewById(R.id.cvMenuItem);

        //itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    @Override
    public void onClick(View v) {
    itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
