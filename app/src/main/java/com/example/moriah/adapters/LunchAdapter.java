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
import com.example.moriah.model.Lunch;
import com.example.moriah.viewholders.LunchViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LunchAdapter extends RecyclerView.Adapter<LunchViewHolder> {

    private List<Lunch> lunchList;
    private Context mContext;
    private String TAG = "LunchAdapter";


    public LunchAdapter(Context context, List<Lunch> lunchList){
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

        Lunch lunch = lunchList.get(position);
        holder.txtlunchname.setText(lunch.getName());
        holder.txtprice.setText(lunch.getPrice());
        final int radius = 40;
        final int margin = 0;
//        final Transformation transformation = new RoundedCornersTransformation(radius, margin);
//        Picasso.get().load(breakfast.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).transform(transformation).into(holder.imageView);
        Picasso.get().load(lunch.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).into(holder.imageView);

        holder.cvlunchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: clicked on: " + lunchList.get(position));

                if(!lunch.getName().equals("")){
                    Intent intent = new Intent(mContext, FoodDetail.class);
                    intent.putExtra("image_url", lunch.getImage());
                    intent.putExtra("image_name", lunch.getName());
                    intent.putExtra("item_price", lunch.getPrice());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lunchList ==null?0: lunchList.size();
    }
}
