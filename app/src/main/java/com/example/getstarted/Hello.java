package com.example.getstarted;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.getstarted.Models.Usermodels;
import com.example.getstarted.activities.Homepage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public class Hello extends AppCompatActivity {
    private TextView welcometxt;
    private TextView cardnm;
    private Button nxt;
    FirebaseDatabase data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_hello);
        data = FirebaseDatabase.getInstance();
        welcometxt = findViewById(R.id.welcomeText);
        cardnm = findViewById(R.id.cardName);
        nxt = findViewById(R.id.toLoct);
        data.getReference().child("Admin").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        Usermodels usermodels = snapshot.getValue(Usermodels.class);
                        welcometxt.setText("Hello " + usermodels.getName());
                        cardnm.setText(usermodels.getName());
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentl = new Intent(Hello.this,location.class);
                startActivity(intentl);
            }
        });
    }

}
