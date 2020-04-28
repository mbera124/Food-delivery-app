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


   public BreakfastAdapter(Context context, List<Breakfast> breakfastList){
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
       String currInit = "Ksh. ";
       Breakfast breakfast = breakfastList.get(position);
       holder.txtbreakfastname.setText(breakfast.getName());
       holder.txtprice.setText(currInit + breakfast.getPrice());
//        final int radius = 30;
//        final int margin = 0;
//       final Transformation transformation = new RoundedCornersTransformation(radius, margin);
//        Picasso.get().load(breakfast.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).transform(transformation).into(holder.imageView);
      Picasso.get().load(breakfast.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).into(holder.imageView);

        holder.cvbreakfastItem.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     Log.d(TAG,"onClick: clicked on: " + breakfastList.get(position));

                                                     if(!breakfast.getName().equals("")){
                                                             Intent intent = new Intent(mContext, FoodDetail.class);
                                                             intent.putExtra("image_url", breakfast.getImage());
                                                             intent.putExtra("item_name", breakfast.getName());
                                                             intent.putExtra("item_price", breakfast.getPrice());
                                                             mContext.startActivity(intent);
                                                         }
                                                 }
                                             });
    }

    @Override
    public int getItemCount() {
        return breakfastList ==null?0: breakfastList.size();
    }
}
