package com.example.getstarted;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.BreakIterator;


public class newproductsFragment extends Fragment {



    public newproductsFragment() {
        // Required empty public constructor
    }
    Button button;
    public static TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newproducts, container, false);
        button = root.findViewById(R.id.barcode);
        textView = root.findViewById(R.id.textname);
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), com.example.getstarted.scannerView.class));
            }
        });*/
        return root;
    }
}