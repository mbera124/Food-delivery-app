package com.example.moriah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.model.Breakfast;
import com.example.moriah.viewholders.BreakfastViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastViewHolder> {

    private List<Breakfast> breakfastList;
    private Context mContext;

    public BreakfastAdapter(Context context, List<Breakfast> breakfastList) {
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
        Breakfast breakfast =breakfastList.get(position);
        holder.txtbreakfastname.setText(breakfast.getName());
        Picasso.get().load(breakfast.getImage()).placeholder(R.drawable.breakfast).into(holder.imageView);

        holder.cvbreakfastitem.setOnClickListener(v -> Toast.makeText(mContext, "You just clicked" +" "+ breakfast.getName(),
                Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return breakfastList==null?0:breakfastList.size();
    }
}
