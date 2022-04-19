package com.example.getstarted.ui.category;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.getstarted.R;
import com.example.getstarted.activities.ViewAllActivity;


public class CategoryFragment extends Fragment {

    Button softdrinks,vegetables,toys,beauty,stationary,grains,clothes,packedfood,sports;
    private Context context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category,container,false);

        context = getContext();
        Button fruits = root.findViewById(R.id.fruitss);
        vegetables = root.findViewById(R.id.vegetables);
        softdrinks = root.findViewById(R.id.softdrinks);
        toys = root.findViewById(R.id.toys);
        beauty = root.findViewById(R.id.beauty);
        stationary = root.findViewById(R.id.stationary);
        clothes = root.findViewById(R.id.clothes);
        grains = root.findViewById(R.id.grains);
        packedfood = root.findViewById(R.id.packed_food);
        sports = root.findViewById(R.id.sports);

        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type","fruits");
                startActivity(intent);
            }
        });
        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type","vegetables");
                startActivity(intent);
            }
        });
        toys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type","toys");
                startActivity(intent);
            }
        });
        softdrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type","softdrinks");
                startActivity(intent);
            }
        });
        stationary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type","stationary");
                startActivity(intent);
            }
        });
        grains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type","grains");
                startActivity(intent);
            }
        });
        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type","clothes");
                startActivity(intent);
            }
        });
        packedfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type","packedfood");
                startActivity(intent);
            }
        });
        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type","beauty");
                startActivity(intent);
            }
        });

        return root;

    }
}