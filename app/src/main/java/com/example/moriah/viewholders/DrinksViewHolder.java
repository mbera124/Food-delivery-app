package com.example.moriah.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;

public class DrinksViewHolder extends RecyclerView.ViewHolder {

    public TextView txtdrinkname;
    public ImageView imageView;
    public CardView cvdrinkitem;
    private ItemClickListener itemClickListener;
    public DrinksViewHolder(@NonNull View itemView) {
        super(itemView);

        txtdrinkname=itemView.findViewById(R.id.drink_name);
        imageView=itemView.findViewById(R.id.drink_image);
        cvdrinkitem=itemView.findViewById(R.id.cvdrinkitem);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}


