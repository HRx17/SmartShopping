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
import com.example.getstarted.Models.CartModel;
import com.example.getstarted.Models.ViewAllModel;
import com.example.getstarted.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CartAdaptor extends RecyclerView.Adapter<CartAdaptor.ViewHolder>{

    Context context;
    FirebaseAuth auth;
    FirebaseFirestore db;
    List<CartModel> list;
    int totalprice =0;

    public CartAdaptor(Context context, List<CartModel> list) {
        this.context = context;
        this.list = list;
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public CartAdaptor.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new CartAdaptor.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartAdaptor.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getProductName());
        holder.price.setText(String.valueOf(list.get(position).getTotalPrice()));
        holder.quantity.setText(list.get(position).getTotalQuantity());
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.kg.setText(list.get(position).getProductPrice());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("addtoCart")
                        .document(list.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    list.remove(list.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item Deleted!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(context, "Error!"+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        totalprice = totalprice + list.get(position).getTotalPrice();
        Intent intent = new Intent("MytotalPrice");
        intent.putExtra("totalprice",totalprice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView delete;
        TextView name,quantity,price,kg,date,time;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_name);
            quantity = itemView.findViewById(R.id.cart_q);
            price = itemView.findViewById(R.id.cart_p);
            time = itemView.findViewById(R.id.cart_t);
            date = itemView.findViewById(R.id.cart_d);
            kg = itemView.findViewById(R.id.cart_price);
            delete = itemView.findViewById(R.id.deletecart);
        }
    }
}

