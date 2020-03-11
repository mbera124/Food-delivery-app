package com.example.moriah.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.activities.FoodDetail;
import com.example.moriah.model.Lunch;
import com.example.moriah.viewholders.LunchViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LunchAdapter extends RecyclerView.Adapter<LunchViewHolder> {

    private List<Lunch> lunchList;
    private Context mContext;
    private String TAG = "LunchAdapter";


    public LunchAdapter(Context context, List<Lunch> lunchList) {
        this.mContext = context;
        this.lunchList = lunchList;
    }

    @NonNull
    @Override
    public LunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lunch_items, parent, false);
        return new LunchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LunchViewHolder holder, int position) {
        Lunch lunch =lunchList.get(position);
        holder.txtlunchname.setText(lunch.getName());
        Picasso.get().load(lunch.getImage()).placeholder(R.drawable.lunch).into(holder.imageView);

        holder.cvlunchitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"onClick: clicked on: " + lunchList.get(position));

                if(!lunch.getName().equals("")){
                    if(lunch.getName().toLowerCase().equals("rice")){
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url",lunch.getImage());
                        intent.putExtra("item_name",lunch.getName());
                        intent.putExtra("item_price",lunch.getPrice());
                        mContext.startActivity(intent);
                    }
                    else if(lunch.getName().toLowerCase().equals("fries")){
                        Intent intent = new Intent(mContext,FoodDetail.class);
                        intent.putExtra("image_url",lunch.getImage());
                        intent.putExtra("item_name",lunch.getName());
                        intent.putExtra("item_price",lunch.getPrice());
                        mContext.startActivity(intent);
                    }  else if(lunch.getName().toLowerCase().equals("masala")){
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url",lunch.getImage());
                        intent.putExtra("item_name",lunch.getName());
                        intent.putExtra("item_price",lunch.getPrice());
                        intent.putExtra("item_price",lunch.getPrice());
                        mContext.startActivity(intent);
                    } else if(lunch.getName().toLowerCase().equals("chicken")) {
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url", lunch.getImage());
                        intent.putExtra("item_name", lunch.getName());
                        intent.putExtra("item_price",lunch.getPrice());
                        mContext.startActivity(intent);
                    }
                    else if(lunch.getName().toLowerCase().equals("pork ribs")) {
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url", lunch.getImage());
                        intent.putExtra("item_name", lunch.getName());
                        intent.putExtra("item_price",lunch.getPrice());
                        mContext.startActivity(intent);
                    }
                    else if(lunch.getName().toLowerCase().equals("beef")) {
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url", lunch.getImage());
                        intent.putExtra("item_name", lunch.getName());
                        intent.putExtra("item_price",lunch.getPrice());
                        mContext.startActivity(intent);
                    }

                    else {
                        if(lunch.getName().toLowerCase().equals("pilau")) {
                            Intent intent = new Intent(mContext, FoodDetail.class);
                            intent.putExtra("image_url", lunch.getImage());
                            intent.putExtra("item_name", lunch.getName());
                            intent.putExtra("item_price",lunch.getPrice());
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
        return lunchList.size();
    }
}
