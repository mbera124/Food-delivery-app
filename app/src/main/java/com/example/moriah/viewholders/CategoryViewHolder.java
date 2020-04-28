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

public class CategoryViewHolder extends RecyclerView.ViewHolder{
    public TextView txtcategoryname;
    public ImageView imageView;
    public CardView cvcategoryItem;

    private ItemClickListener itemClickListener;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        txtcategoryname = itemView.findViewById(R.id.category_name);
        imageView = itemView.findViewById(R.id.category_image);
        cvcategoryItem = itemView.findViewById(R.id.cvcategoryItem);
    }

        public void bind(final Category category, CategoryAdapter.onItemClicklistener onItemClick) {
            txtcategoryname.setText(category.getName());
            Picasso.get().load(category.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).into(imageView);
//            Picasso.get().load(category.getImage()).fit().centerCrop().placeholder(R.drawable.raspberry).into(holder.imageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(category);

                  // Intent intent = new Intent(itemView.getContext(), Category.class);
                   // intent.putExtra("image_url", category.getImage());
                   // intent.putExtra("image_name", category.getName());
                   // itemView.getContext().startActivity(intent);

                }
            });
    }
}
