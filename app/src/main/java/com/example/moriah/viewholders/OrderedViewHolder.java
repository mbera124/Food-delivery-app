package com.example.moriah.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;

public class OrderedViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtmealname, mealquantity,mealprice,total;
    public ImageView imageView;
    public CardView cvbreakfastItem;

    private ItemClickListener itemClickListener;

    public OrderedViewHolder(@NonNull View itemView) {
        super(itemView);

        txtmealname=itemView.findViewById(R.id.meal_name);
//        total=itemView.findViewById(R.id.totalmeal);
        mealprice=itemView.findViewById(R.id.meal_price);
        mealquantity=itemView.findViewById(R.id.meal_Quantity);
        cvbreakfastItem = itemView.findViewById(R.id.cvbreakfastitem);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
