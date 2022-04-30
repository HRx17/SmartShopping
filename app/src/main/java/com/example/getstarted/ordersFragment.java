package com.example.getstarted;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.getstarted.Models.CartModel;
import com.example.getstarted.Models.OrderModel;
import com.example.getstarted.Models.ViewAllModel;
import com.example.getstarted.adaptors.CartAdaptor;
import com.example.getstarted.adaptors.OrderAdaptor;
import com.example.getstarted.adaptors.ViewAllAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ordersFragment extends Fragment {


    FirebaseFirestore db;
    FirebaseAuth auth;
    LinearLayout empty;
    RecyclerView recyclerView;
    OrderAdaptor orderAdaptor;
    List<OrderModel> orderModelList;

    public ordersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_orders, container, false);
        empty = root.findViewById(R.id.empty_order);
        recyclerView = root.findViewById(R.id.rec_orders);
        recyclerView.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        orderModelList = new ArrayList<>();
        orderAdaptor = new OrderAdaptor(getActivity(), orderModelList);
        recyclerView.setAdapter(orderAdaptor);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("addtoCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        OrderModel orderModel = documentSnapshot.toObject(OrderModel.class);
                        orderModelList.add(orderModel);
                        orderAdaptor.notifyDataSetChanged();
                        if(orderModel != null){
                            empty.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        else{
                            empty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        return root;
    }

}