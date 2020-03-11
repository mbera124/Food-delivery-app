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
import com.example.moriah.model.SoftDrinks;
import com.example.moriah.viewholders.DrinksViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinksAdapter extends RecyclerView.Adapter<DrinksViewHolder> {
    private List<SoftDrinks> drinksList;
    private Context mContext;
    private String TAG = "DrinksAdapter";

    public DrinksAdapter(Context context, List<SoftDrinks> drinksList) {
        this.mContext = context;
        this.drinksList = drinksList;
    }


    @NonNull
    @Override
    public DrinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.softdrinks_items, parent, false);
        return new DrinksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinksViewHolder holder, int position) {

        SoftDrinks softDrinks =drinksList.get(position);
        holder.txtdrinkname.setText(softDrinks.getName());
        Picasso.get().load(softDrinks.getImage()).placeholder(R.drawable.lunch).into(holder.imageView);

        holder.cvdrinkitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: clicked on: " + drinksList.get(position));

                if(!softDrinks.getName().equals("")){
                    if(softDrinks.getName().toLowerCase().equals("coke")){
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url",softDrinks.getImage());
                        intent.putExtra("item_name",softDrinks.getName());
                        intent.putExtra("item_price",softDrinks.getPrice());
                        mContext.startActivity(intent);
                    }
                    else if(softDrinks.getName().toLowerCase().equals("lime water")){
                        Intent intent = new Intent(mContext,FoodDetail.class);
                        intent.putExtra("image_url",softDrinks.getImage());
                        intent.putExtra("item_name",softDrinks.getName());
                        intent.putExtra("item_price",softDrinks.getPrice());
                        mContext.startActivity(intent);
                    }  else if(softDrinks.getName().toLowerCase().equals("orange juice")){
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url",softDrinks.getImage());
                        intent.putExtra("item_name",softDrinks.getName());
                        intent.putExtra("item_price",softDrinks.getPrice());
                        mContext.startActivity(intent);
                    } else if(softDrinks.getName().toLowerCase().equals("raspberry smoothie")) {
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url", softDrinks.getImage());
                        intent.putExtra("item_name", softDrinks.getName());
                        intent.putExtra("item_price",softDrinks.getPrice());
                        mContext.startActivity(intent);
                    }
                    else if(softDrinks.getName().toLowerCase().equals("fruit blend smoothie")) {
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url", softDrinks.getImage());
                        intent.putExtra("item_name", softDrinks.getName());
                        intent.putExtra("item_price",softDrinks.getPrice());
                        mContext.startActivity(intent);
                    }
                    else if(softDrinks.getName().toLowerCase().equals("water")) {
                        Intent intent = new Intent(mContext, FoodDetail.class);
                        intent.putExtra("image_url", softDrinks.getImage());
                        intent.putExtra("item_name", softDrinks.getName());
                        intent.putExtra("item_price",softDrinks.getPrice());
                        mContext.startActivity(intent);
                    }

                    else {
                        if(softDrinks.getName().toLowerCase().equals("milk")) {
                            Intent intent = new Intent(mContext, FoodDetail.class);
                            intent.putExtra("image_url", softDrinks.getImage());
                            intent.putExtra("item_name", softDrinks.getName());
                            intent.putExtra("item_price",softDrinks.getPrice());
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
        return drinksList.size();
    }
}
