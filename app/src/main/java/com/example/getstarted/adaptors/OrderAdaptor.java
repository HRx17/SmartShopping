package com.example.getstarted.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getstarted.Models.OrderModel;
import com.example.getstarted.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderAdaptor extends RecyclerView.Adapter<OrderAdaptor.ViewHolder> {
    Context context;
    FirebaseAuth auth;
    FirebaseFirestore db;
    List<OrderModel> list;
    public OrderAdaptor(Context context, List<OrderModel> list) {
        this.context = context;
        this.list = list;
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public OrderAdaptor.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new OrderAdaptor.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.number.setText(String.valueOf(list.get(position).getOrder_number()));
        holder.date.setText(String.valueOf(list.get(position).getDate()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView number,date;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.order_date);
            number = itemView.findViewById(R.id.order_number);
        }
    }
}
