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
import com.example.getstarted.Models.OfferModel;
import com.example.getstarted.Models.ViewAllModel;
import com.example.getstarted.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OfferAdaptor extends RecyclerView.Adapter<OfferAdaptor.ViewHolder>{

    Context context;
    List<OfferModel> list;

    public OfferAdaptor(Context context, List<OfferModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public OfferAdaptor.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new OfferAdaptor.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OfferAdaptor.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.img);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(String.valueOf(list.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,price;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
           img = itemView.findViewById(R.id.offer_img);
           name = itemView.findViewById(R.id.offer_name);
           price = itemView.findViewById(R.id.offer_discount);
        }
    }
}

