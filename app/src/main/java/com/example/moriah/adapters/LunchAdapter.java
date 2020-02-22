package com.example.moriah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.model.Lunch;
import com.example.moriah.viewholders.LunchViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LunchAdapter extends RecyclerView.Adapter<LunchViewHolder> {

    private List<Lunch> lunchList;
    private Context mContext;


    public LunchAdapter(Context context, List<Lunch> lunchList) {
        this.mContext = context;
        this.lunchList = lunchList;
    }

    @NonNull
    @Override
    public LunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lunch_items, parent, false);
        return new LunchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LunchViewHolder holder, int position) {
        Lunch lunch =lunchList.get(position);
        holder.txtlunchname.setText(lunch.getName());
        Picasso.get().load(lunch.getImage()).placeholder(R.drawable.lunch).into(holder.imageView);

        holder.cvlunchitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You just clicked" + " " + lunch.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lunchList.size();
    }
}
