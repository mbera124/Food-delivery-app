package com.example.moriah.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;

public class SoftDrinksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
public TextView txtdrinkname, txtprice;
public ImageView imageView;
public CardView cvdrinkItem;

private ItemClickListener itemClickListener;

public SoftDrinksViewHolder(@NonNull View itemView) {
        super(itemView);

        txtdrinkname=itemView.findViewById(R.id.drink_name);
        txtprice=itemView.findViewById(R.id.etdrinkprice);
        imageView=itemView.findViewById(R.id.drink_image);
        cvdrinkItem = itemView.findViewById(R.id.cvdrinkItem);

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
