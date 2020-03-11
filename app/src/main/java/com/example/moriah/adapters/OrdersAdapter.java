package com.example.moriah.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.model.Order;
import com.example.moriah.model.Request;
import com.example.moriah.viewholders.OrdersViewHolder;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersViewHolder> {

    private List<Request> requestList;
    private Context mContext;
    private String TAG = "OrdersAdapter";


    public OrdersAdapter(List<Request> requestList, Context mContext) {
        this.requestList = requestList ;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_layout, parent, false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Request request=requestList.get(position);
        holder.txtordername.setText(request.getProductName());
        holder.txtorderid.setText(request.getProductId());
        holder.txtorderstatus.setText(request.getStatus());
        holder.txtorderaddress.setText(request.getAddress());

        holder.cvorderitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: clicked on: " + requestList.get(position));
                Toast.makeText(mContext, "You just clicked" + " " + request.getName(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
