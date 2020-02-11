package com.example.moriah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Home;
import com.example.moriah.R;
import com.example.moriah.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

   private List<Category> categoryList;
   private Context mContext;


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
        Picasso.get().load(category.getImage()).placeholder(R.drawable.raspberry).into(holder.imageView);

        holder.cvMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You just clicked" +" "+ category.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList==null?0:categoryList.size();
    }
}
