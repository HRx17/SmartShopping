package com.example.getstarted.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.getstarted.Models.CategoryModel;
import com.example.getstarted.Models.Recommended;
import com.example.getstarted.R;
import com.example.getstarted.activities.ViewAllActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecommendedAdaptor extends RecyclerView.Adapter<RecommendedAdaptor.ViewHolder> {
    @NonNull
    @NotNull
    private Context context;
    private List<Recommended> recommendedList;

    public RecommendedAdaptor(Context context, List<Recommended> recommendedList) {
        this.context = context;
        this.recommendedList = recommendedList;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new RecommendedAdaptor.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Glide.with(context).load(recommendedList.get(position).getImg_url()).into(holder.rec_img);
        holder.name.setText(recommendedList.get(position).getName());
        holder.description.setText(recommendedList.get(position).getDescription());
        holder.rating.setText(recommendedList.get(position).getRating());
        holder.discount.setText(recommendedList.get(position).getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type",recommendedList.get(position).getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,description,rating,discount;
        ImageView rec_img;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rec_name);
            rec_img =itemView.findViewById(R.id.rec_image);
            description = itemView.findViewById(R.id.rec_des);
            rating = itemView.findViewById(R.id.rec_rating);
            discount = itemView.findViewById(R.id.rec_discount);

        }
    }
}
