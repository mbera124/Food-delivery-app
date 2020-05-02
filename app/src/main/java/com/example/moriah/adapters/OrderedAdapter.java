package com.example.moriah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.model.Request;
import com.example.moriah.viewholders.OrderedViewHolder;

import java.util.List;

public class OrderedAdapter extends RecyclerView.Adapter<OrderedViewHolder> {

private List<Request> requestList;
private Context mContext;
private String TAG = "OrderedAdapter";


public OrderedAdapter(Context context, List<Request> requestList){
        this.mContext = context;
        this.requestList = requestList;
        }

@NonNull
@Override
public OrderedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ordered_items, parent, false);
        return new OrderedViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull OrderedViewHolder holder, int position) {
        String currInit = "Qty. ";
        String currIt = "Name: ";
        String currtotal = "Total=Ksh. ";

        Request request = requestList.get(position);
        holder.txtmealname.setText(currIt+request.getProductName());
        holder.mealquantity.setText(currInit+request.getQuantity());
        holder.mealprice.setText(currtotal+request.getTotalOrderPrice());
//        holder.total.setText(currtotal+request.getTotal());
//        final int radius = 30;
//        final int margin = 0;
//       final Transformation transformation = new RoundedCornersTransformation(radius, margin);
//        Picasso.get().load(breakfast.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).transform(transformation).into(holder.imageView);
//        Picasso.get().load(order.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).into(holder.imageView);

//        holder.cvbreakfastItem.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        Log.d(TAG,"onClick: clicked on: " + orderList.get(position));
//
////        if(!breakfast.getName().equals("")){
////        Intent intent = new Intent(mContext, FoodDetail.class);
////        intent.putExtra("image_url", breakfast.getImage());
////        intent.putExtra("item_name", breakfast.getName());
////        intent.putExtra("item_price", breakfast.getPrice());
////        mContext.startActivity(intent);
////        }
//        }
//        });
        }

@Override
public int getItemCount() {
        return requestList ==null?0: requestList.size();
        }
        }
