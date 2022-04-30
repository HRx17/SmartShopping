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

import com.example.getstarted.Models.OfferModel;
import com.example.getstarted.Models.OfferModel;
import com.example.getstarted.Models.ViewAllModel;
import com.example.getstarted.adaptors.OfferAdaptor;
import com.example.getstarted.adaptors.OfferAdaptor;
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


public class offersFragment extends Fragment {


    FirebaseFirestore db;
    FirebaseAuth auth;
    LinearLayout empty,offer;
    RecyclerView recyclerView;
    OfferAdaptor offerAdaptor;
    List<OfferModel> offerModelList;

    public offersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offers2, container, false);
        empty = root.findViewById(R.id.offer_empty);
        offer = root.findViewById(R.id.offer_lin);
        offer.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.rec_offer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        offerModelList = new ArrayList<>();
        offerAdaptor = new OfferAdaptor(getActivity(), offerModelList);
        recyclerView.setAdapter(offerAdaptor);

        db.collection("offers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull  Task<QuerySnapshot>task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        String documentId = documentSnapshot.getId();
                        OfferModel offerModel = documentSnapshot.toObject(OfferModel.class);
                        offerModel.setDocumentId(documentId);
                        offerModelList.add(offerModel);
                        offerAdaptor.notifyDataSetChanged();
                        if(offerModel != null){
                            empty.setVisibility(View.GONE);
                            offer.setVisibility(View.VISIBLE);
                        }
                        else{
                            empty.setVisibility(View.VISIBLE);
                            offer.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });


        return root;
    }

}