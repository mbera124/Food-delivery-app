package com.example.moriah.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;

public class LunchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtlunchname, txtprice;
    public ImageView imageView;
    public CardView cvlunchItem;

    private ItemClickListener itemClickListener;

    public LunchViewHolder(@NonNull View itemView) {
        super(itemView);

        txtlunchname=itemView.findViewById(R.id.lunch_name);
        txtprice=itemView.findViewById(R.id.etlunchprice);
        imageView=itemView.findViewById(R.id.lunch_image);
        cvlunchItem = itemView.findViewById(R.id.cvlunchItem);

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
