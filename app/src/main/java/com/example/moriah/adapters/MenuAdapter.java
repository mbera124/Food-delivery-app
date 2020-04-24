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
import com.example.moriah.model.Category;
import com.example.moriah.viewholders.MenuViewHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

   private List<Category> categoryList;
   private Context mContext;
   private String TAG = "MenuAdapter";


   public MenuAdapter(Context context, List<Category> categoryList){
       this.mContext = context;
       this.categoryList = categoryList;
   }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

       Category category = categoryList.get(position);
       holder.txtmenuname.setText(category.getName());
        final int radius = 40;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius, margin);
        Picasso.get().load(category.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).transform(transformation).into(holder.imageView);

        holder.cvMenuItem.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     Log.d(TAG,"onClick: clicked on: " + categoryList.get(position));

                                                     if(!category.getName().equals("")){
                                                             Intent intent = new Intent(mContext, FoodDetail.class);
                                                             intent.putExtra("image_url",category.getImage());
                                                             intent.putExtra("image_name",category.getName());
                                                             intent.putExtra("item_price",category.getPrice());
                                                             mContext.startActivity(intent);
                                                         }
                                                 }
                                             });
    }

    @Override
    public int getItemCount() {
        return categoryList==null?0:categoryList.size();
    }
}
