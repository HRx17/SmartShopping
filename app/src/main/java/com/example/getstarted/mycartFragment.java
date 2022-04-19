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
import com.example.getstarted.Models.ViewAllModel;
import com.example.getstarted.adaptors.CartAdaptor;
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


public class mycartFragment extends Fragment {


    FirebaseFirestore db;
    TextView total;
    Button button;
    FirebaseAuth auth;
    LinearLayout empty,cart,head;
    RecyclerView recyclerView;
    CartAdaptor cartAdaptor;
    List<CartModel> cartModelList;
    String bill;

    public mycartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mycart, container, false);
        empty = root.findViewById(R.id.cart_empty);
        button = root.findViewById(R.id.buynow);
        button.setVisibility(View.GONE);
        head = root.findViewById(R.id.pricehead);
        head.setVisibility(View.GONE);
        cart = root.findViewById(R.id.cart_lin);
        cart.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance();
        total = root.findViewById(R.id.totalcart);
        db = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.rec_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageReceiver ,new IntentFilter("MytotalPrice"));

        cartModelList = new ArrayList<>();
        cartAdaptor = new CartAdaptor(getActivity(), cartModelList);
        recyclerView.setAdapter(cartAdaptor);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("addtoCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        String documentId = documentSnapshot.getId();
                        CartModel cartModel = documentSnapshot.toObject(CartModel.class);
                        cartModel.setDocumentId(documentId);
                        cartModelList.add(cartModel);
                        cartAdaptor.notifyDataSetChanged();
                        if(cartModel != null){
                            empty.setVisibility(View.GONE);
                            cart.setVisibility(View.VISIBLE);
                            head.setVisibility(View.VISIBLE);
                            button.setVisibility(View.VISIBLE);
                        }
                        else{
                            empty.setVisibility(View.VISIBLE);
                            cart.setVisibility(View.GONE);
                            head.setVisibility(View.GONE);
                            button.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), gateway.class);
                intent.putExtra("itemlist",(Serializable)cartModelList);
                intent.putExtra("total",bill);
                startActivity(intent);
            }
        });

        return root;
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalbill = intent.getIntExtra("totalprice",0);
            bill = String.valueOf(totalbill);
            total.setText("Total Bill: Rs. "+totalbill);
        }
    };
}