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
import com.example.moriah.model.SoftDrinks;
import com.example.moriah.viewholders.SoftDrinksViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SoftDrinksAdapter  extends RecyclerView.Adapter<SoftDrinksViewHolder> {

    private List<SoftDrinks> softDrinksList;
    private Context mContext;
    private String TAG = "SoftDrinksAdapter";


    public SoftDrinksAdapter(Context context, List<SoftDrinks> softDrinksList){
        this.mContext = context;
        this.softDrinksList = softDrinksList;
    }

    @NonNull
    @Override
    public SoftDrinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.softdrinks_items, parent, false);
        return new SoftDrinksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoftDrinksViewHolder holder, int position) {

        SoftDrinks softDrinks = softDrinksList.get(position);
        holder.txtdrinkname.setText(softDrinks.getName());
        holder.txtprice.setText(softDrinks.getPrice());
        final int radius = 40;
        final int margin = 0;
//        final Transformation transformation = new RoundedCornersTransformation(radius, margin);
//        Picasso.get().load(breakfast.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).transform(transformation).into(holder.imageView);
        Picasso.get().load(softDrinks.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).into(holder.imageView);

        holder.cvdrinkItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: clicked on: " + softDrinksList.get(position));

                if(!softDrinks.getName().equals("")){
                    Intent intent = new Intent(mContext, FoodDetail.class);
                    intent.putExtra("image_url", softDrinks.getImage());
                    intent.putExtra("image_name", softDrinks.getName());
                    intent.putExtra("item_price", softDrinks.getPrice());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return softDrinksList ==null?0: softDrinksList.size();
    }
}
