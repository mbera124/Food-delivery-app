package com.example.moriah.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.moriah.R;
import com.example.moriah.model.Breakfast;
import com.example.moriah.model.Order;
import com.example.moriah.viewholders.BreakfastViewHolder;
import com.example.moriah.viewholders.CartViewHolder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private  static List<Order> listData= new ArrayList<>();
    private Context mContext;


    public CartAdapter(List<Order> listData, Context mContext) {
        this.listData = listData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        TextDrawable drawable=TextDrawable.builder()
                .buildRound(""+listData.get(position).getQuantity(), Color.RED);

        Order order=listData.get(position);
        holder.imageView.setImageDrawable(drawable);
        Locale locale=new Locale("en","ke");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
        int price=(Integer.parseInt(listData.get(position).getUnitPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.txtcartitemprice.setText(fmt.format(price));
        holder.txtcartitemname.setText(listData.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData==null?0:listData.size();
    }
}
