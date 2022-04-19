package com.example.getstarted;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.getstarted.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class logoutFragment extends Fragment {


    public logoutFragment() {
    }

    Button button;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        context = getActivity();
        button = root.findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}