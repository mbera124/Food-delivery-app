package com.example.moriah.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;

public class LunchViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtlunchname;
    public ImageView imageView;
    public CardView cvlunchitem;

    private ItemClickListener itemClickListener;

    public LunchViewHolder(@NonNull View itemView) {
        super(itemView);
        txtlunchname=itemView.findViewById(R.id.lunch_name);
        imageView=itemView.findViewById(R.id.lunch_image);
        cvlunchitem = itemView.findViewById(R.id.cvlunchitem);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
