package com.example.moriah.viewholders;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;
import com.example.moriah.model.Common;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
    public TextView txtcartitemname,txtcartitemprice;
    public ImageView imageView;
    public CardView cvcartitem;

    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtcartitemname=itemView.findViewById(R.id.cart_item_name);
        txtcartitemprice=itemView.findViewById(R.id.cart_item_price);
        imageView=itemView.findViewById(R.id.cart_item_count);
        cvcartitem= itemView.findViewById(R.id.cvcartlayout);

        itemView.setOnCreateContextMenuListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
menu.setHeaderTitle("Select action");
menu.add(0,0,getAdapterPosition(), Common.DELETE);
    }
}
