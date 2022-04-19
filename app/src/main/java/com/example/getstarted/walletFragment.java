package com.example.getstarted;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.getstarted.Models.CartModel;
import com.example.getstarted.activities.Homepage;
import com.example.getstarted.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class walletFragment extends Fragment {

    public walletFragment() {
    }

    FirebaseAuth auth;
    FirebaseFirestore db;
    Button back,buynow;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);
        back = root.findViewById(R.id.walletoprof);
        buynow = root.findViewById(R.id.buynow);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        context = getActivity();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Homepage.class);
                startActivity(intent);
            }
        });

        return root;
    }
}