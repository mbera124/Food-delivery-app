package com.example.moriah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.model.Category;
import com.example.moriah.viewholders.CategoryViewHolder;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private List<Category> categoryList;
    private Context mContext;
    private String TAG = "CategoryAdapter";

    private onItemClicklistener listener;

    public interface onItemClicklistener{
         void onItemClick(Category category) ;
    }



    public CategoryAdapter(Context context, List<Category> categoryList, onItemClicklistener listener){
        this.mContext = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_items, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category category = categoryList.get(position);
        holder.bind(category,listener);
//        final int radius = 40;
//        final int margin = 0;
//        final Transformation transformation = new RoundedCornersTransformation(radius, margin);
//        Picasso.get().load(category.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).transform(transformation).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return categoryList ==null? 0: categoryList.size();
    }
}
