package com.example.moriah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.model.Request;
import com.example.moriah.viewholders.OrdersViewHolder;

import java.util.List;

public class EditOrderAdapter extends RecyclerView.Adapter<OrdersViewHolder>  {

    private List<Request> requestList;
    private Context mContext;
    private String TAG = "OrdersAdapter";

    public onItemClicklistener listener;

    public interface onItemClicklistener{
        public void onItemClick(Request request) ;
    }


    public EditOrderAdapter(List<Request> requestList, Context mContext, EditOrderAdapter.onItemClicklistener listener) {
        this.requestList = requestList ;
        this.mContext = mContext;
        this.listener=listener;
    }
    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.admin_orders, parent, false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Request request=requestList.get(position);
        holder.bind(request,listener);

    }



    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
