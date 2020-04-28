package com.example.moriah.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;


public class BreakfastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
 public TextView txtbreakfastname, txtprice;
 public ImageView imageView;
 public CardView cvbreakfastItem;

 private ItemClickListener itemClickListener;

    public BreakfastViewHolder(@NonNull View itemView) {
        super(itemView);

        txtbreakfastname=itemView.findViewById(R.id.breakfast_name);
        txtprice=itemView.findViewById(R.id.breakfast_price);
        imageView=itemView.findViewById(R.id.breakfast_image);
        cvbreakfastItem = itemView.findViewById(R.id.cvbreakfastitem);

//        cvMenuItem.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
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
