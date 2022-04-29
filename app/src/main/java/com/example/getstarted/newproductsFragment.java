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

import com.example.getstarted.Models.NewProductModel;
import com.example.getstarted.Models.NewProductModel;
import com.example.getstarted.Models.ViewAllModel;
import com.example.getstarted.adaptors.NewProductAdaptor;
import com.example.getstarted.adaptors.NewProductAdaptor;
import com.example.getstarted.adaptors.ViewAllAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;


public class newproductsFragment extends Fragment {


    public static BreakIterator textView;
    FirebaseFirestore db;
    FirebaseAuth auth;
    LinearLayout empty,newproduct;
    RecyclerView recyclerView;
    NewProductAdaptor newproductAdaptor;
    List<NewProductModel> newproductModelList;

    public newproductsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newproducts, container, false);
        empty = root.findViewById(R.id.newproduct_empty);
        newproduct = root.findViewById(R.id.newproduct_lin);
        newproduct.setVisibility(View.VISIBLE);
        empty.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.rec_newproduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        newproductModelList = new ArrayList<>();
        newproductAdaptor = new NewProductAdaptor(getActivity(), newproductModelList);
        recyclerView.setAdapter(newproductAdaptor);

        db.collection("NewProduct").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        String documentId = documentSnapshot.getId();
                        NewProductModel newproductModel = documentSnapshot.toObject(NewProductModel.class);
                        newproductModel.setDocumentId(documentId);
                        newproductModelList.add(newproductModel);
                        newproductAdaptor.notifyDataSetChanged();
                        if(newproductModel != null){
                            empty.setVisibility(View.GONE);
                            newproduct.setVisibility(View.VISIBLE);
                        }
                        else{
                            empty.setVisibility(View.VISIBLE);
                            newproduct.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
        return root;
    }
}