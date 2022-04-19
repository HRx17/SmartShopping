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
import com.example.getstarted.Models.PopularModel;
import com.example.getstarted.R;
import com.example.getstarted.activities.ViewAllActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {
    @NonNull
    @NotNull

    private Context context;
    private List<CategoryModel> CategoryList;

    public CategoryAdaptor(Context context, List<CategoryModel> CategoryList) {
        this.context = context;
        this.CategoryList = CategoryList;
    }

    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cat_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Glide.with(context).load(CategoryList.get(position).getImg_url()).into(holder.cat_img);
        holder.name.setText(CategoryList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type",CategoryList.get(position).getType());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return CategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cat_img;
        TextView name;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cat_img = itemView.findViewById(R.id.home_cat_img);
            name = itemView.findViewById(R.id.home_cat_name);
        }
    }
}
