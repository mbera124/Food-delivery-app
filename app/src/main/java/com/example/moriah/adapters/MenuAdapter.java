package com.example.moriah.adapters;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.activities.BreakfastActivity;
import com.example.moriah.activities.Login;
import com.example.moriah.activities.Signup;
import com.example.moriah.model.Breakfast;
import com.example.moriah.model.Category;
import com.example.moriah.viewholders.MenuViewHolder;
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

        holder.cvMenuItem.setOnClickListener(v ->
//                Log.d(Tag,"onClick: clicked on: " + categoryList.get(position));
                Toast.makeText(mContext, "You just clicked" +" "+ category.getName(),
                Toast.LENGTH_SHORT).show());

//        Intent intent=new Intent(mContext,BreakfastActivity.class);
//        intent.putExtra("image_url",category.getImage());
//        intent.putExtra("image_name",category.getName());
//        mContext.startActivity(intent);



    }

    @Override
    public int getItemCount() {
        return categoryList==null?0:categoryList.size();
    }
}
