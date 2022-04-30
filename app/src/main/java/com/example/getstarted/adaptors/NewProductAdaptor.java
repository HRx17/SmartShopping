package com.example.getstarted.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.getstarted.Models.NewProductModel;
import com.example.getstarted.Models.NewProductModel;
import com.example.getstarted.Models.ViewAllModel;
import com.example.getstarted.R;
import com.example.getstarted.activities.ViewAllActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewProductAdaptor extends RecyclerView.Adapter<NewProductAdaptor.ViewHolder>{

    Context context;
    FirebaseAuth auth;
    FirebaseFirestore db;
    List<NewProductModel> list;

    public NewProductAdaptor(Context context, List<NewProductModel> list) {
        this.context = context;
        this.list = list;
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public NewProductAdaptor.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new NewProductAdaptor.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.newproduct_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewProductAdaptor.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.img);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type",list.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,description, price;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.newproduct_img);
            name = itemView.findViewById(R.id.newproduct_name);
            description = itemView.findViewById(R.id.newproduct_desc);
            price = itemView.findViewById(R.id.newproduct_price);

        }
    }
}

