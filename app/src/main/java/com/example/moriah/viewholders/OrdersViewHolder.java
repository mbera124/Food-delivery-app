package com.example.moriah.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;

public class OrdersViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView txtorderid,txtorderstatus,txtordername,txtorderaddress;
    public ImageView imageView;
    public CardView cvorderitem;

    private ItemClickListener itemClickListener;

    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);
        txtorderid=itemView.findViewById(R.id.order_id);
        txtorderstatus=itemView.findViewById(R.id.order_status);
        txtordername=itemView.findViewById(R.id.order_name);
        txtorderaddress=itemView.findViewById(R.id.order_address);
        imageView=itemView.findViewById(R.id.order_item_count);
        cvorderitem= itemView.findViewById(R.id.cvcartlayout);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
