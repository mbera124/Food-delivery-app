package com.example.moriah.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.activities.FoodDetail;
import com.example.moriah.model.Breakfast;
import com.example.moriah.viewholders.BreakfastViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastViewHolder> {

    private List<Breakfast> breakfastList;
    private Context mContext;
    private String TAG = "BreakfastAdapter";

    public BreakfastAdapter(Context context, List<Breakfast> breakfastList) {
        this.mContext = context;
        this.breakfastList = breakfastList;

    }
    @NonNull
    @Override
    public BreakfastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.breakfast_items, parent, false);
        return new BreakfastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreakfastViewHolder holder, int position) {
        Breakfast breakfast =breakfastList.get(position);
        holder.txtbreakfastname.setText(breakfast.getName());
        Picasso.get().load(breakfast.getImage()).placeholder(R.drawable.breakfast).into(holder.imageView);

        holder.cvbreakfastitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: clicked on: " + breakfastList.get(position));

                if(!breakfast.getName().equals("")){
                    if(breakfast.getName().toLowerCase().equals("coffee")){
                        Intent intent = new Intent(mContext,FoodDetail.class);
                        intent.putExtra("image_url",breakfast.getImage());
                        intent.putExtra("item_name",breakfast.getName());
                        intent.putExtra("item_price",breakfast.getPrice());
                        mContext.startActivity(intent);
                    }
                    else if(breakfast.getName().toLowerCase().equals("omlette")){
                        Intent intent = new Intent(mContext,FoodDetail.class);
                        intent.putExtra("image_url",breakfast.getImage());
                        intent.putExtra("item_name",breakfast.getName());
                        intent.putExtra("item_price",breakfast.getPrice());
                        mContext.startActivity(intent);
                    }  else if(breakfast.getName().toLowerCase().equals("african tea")){
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url",breakfast.getImage());
                        intent.putExtra("item_name",breakfast.getName());
                        intent.putExtra("item_price",breakfast.getPrice());
                        mContext.startActivity(intent);
                    } else if(breakfast.getName().toLowerCase().equals("sausage")) {
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url", breakfast.getImage());
                        intent.putExtra("item_name", breakfast.getName());
                        intent.putExtra("item_price", breakfast.getPrice());
                        mContext.startActivity(intent);
                    }else if(breakfast.getName().toLowerCase().equals("pancake")) {
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url", breakfast.getImage());
                        intent.putExtra("item_name", breakfast.getName());
                        intent.putExtra("item_price", breakfast.getPrice());
                        mContext.startActivity(intent);
                    }

                    else {
                        if(breakfast.getName().toLowerCase().equals("smokie")) {
                            Intent intent = new Intent(mContext, FoodDetail.class);
                            intent.putExtra("image_url", breakfast.getImage());
                            intent.putExtra("item_name", breakfast.getName());
                            intent.putExtra("item_price", breakfast.getPrice());
                            mContext.startActivity(intent);
//                                                             Toast.makeText(mContext, "You just clicked" + " " + breakfast.getName(),
//                                                                     Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
//

    }

    @Override
    public int getItemCount() {
        return breakfastList==null?0:breakfastList.size();
    }
}
