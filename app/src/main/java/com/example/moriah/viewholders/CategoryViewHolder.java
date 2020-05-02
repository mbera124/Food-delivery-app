package com.example.moriah.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;
import com.example.moriah.adapters.CategoryAdapter;
import com.example.moriah.model.Category;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static android.view.View.GONE;

public class CategoryViewHolder extends RecyclerView.ViewHolder{
    public TextView txtcategoryname;
    public ImageView imageView,imgcheck;
    public CardView cvcategoryItem;

    private ItemClickListener itemClickListener;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        txtcategoryname = itemView.findViewById(R.id.category_name);
        imageView = itemView.findViewById(R.id.category_image);
        imgcheck = itemView.findViewById(R.id.imgcheck);
        cvcategoryItem = itemView.findViewById(R.id.cvcategoryItem);
        imgcheck.setVisibility(GONE);
    }

        public void bind(final Category category, CategoryAdapter.onItemClicklistener onItemClick) {
            txtcategoryname.setText(category.getName());
            final int radius = 40;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius, margin);
//            Picasso.get().load(category.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).into(imageView);
            Picasso.get().load(category.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).into(imageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(category,getAdapterPosition());



                }
            });
    }
}
