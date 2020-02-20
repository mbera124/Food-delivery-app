package com.example.moriah.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;

public class BreakfastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView txtbreakfastname;
    public ImageView imageView;
    public CardView cvbreakfastitem;

    private ItemClickListener itemClickListener;

    public BreakfastViewHolder(@NonNull View itemView) {
        super(itemView);
        txtbreakfastname=itemView.findViewById(R.id.breakfast_name);
        imageView=itemView.findViewById(R.id.breakfast_image);
        cvbreakfastitem = itemView.findViewById(R.id.cvbreakfastitem);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
