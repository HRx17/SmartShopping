package com.example.getstarted.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getstarted.LoginPage;
import com.example.getstarted.R;
import com.example.getstarted.SigninPage;
import com.example.getstarted.adminMain;
import com.example.getstarted.adminlog;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView admin,welcome;
    ImageView imageView;
    Button signin,login;
    ProgressBar progressBar;
    String tokn;
    private Handler mHandler = new Handler();

    public void onBackPressed(){
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        admin = findViewById(R.id.adminlog);
        signin = findViewById(R.id.SigninJump);
        login = findViewById(R.id.LoginJump);
        imageView = findViewById(R.id.imageView);
        welcome = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressshow);
        progressBar.setVisibility(View.VISIBLE);
        signin.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
        welcome.setVisibility(View.GONE);
        admin.setVisibility(View.GONE);

        Animation animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);

        animFadeIn.reset();
        imageView.clearAnimation();
        imageView.startAnimation(animFadeIn);
        animFadeOut.reset();
        imageView.clearAnimation();
        imageView.startAnimation(animFadeOut);
        animFadeIn.reset();
        imageView.clearAnimation();
        imageView.startAnimation(animFadeIn);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                signin.setVisibility(View.VISIBLE);
                login.setVisibility(View.VISIBLE);
                welcome.setVisibility(View.VISIBLE);
                signin.clearAnimation();
                signin.startAnimation(animFadeIn);
                login.clearAnimation();
                login.startAnimation(animFadeIn);
                welcome.clearAnimation();
                welcome.startAnimation(animFadeIn);
                animFadeOut.reset();
                signin.clearAnimation();
                signin.startAnimation(animFadeOut);
                login.clearAnimation();
                login.startAnimation(animFadeOut);
                welcome.clearAnimation();
                welcome.startAnimation(animFadeOut);
                animFadeIn.reset();
                signin.clearAnimation();
                signin.startAnimation(animFadeIn);
                login.clearAnimation();
                login.startAnimation(animFadeIn);
                welcome.clearAnimation();
                welcome.startAnimation(animFadeIn);
                progressBar.setVisibility(View.GONE);
                admin.setVisibility(View.VISIBLE);
            }
        },7000);


        auth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("token",0);
        tokn = sharedPreferences.getString("token",null);

        if(auth.getCurrentUser() != null && tokn =="20216"){
            startActivity(new Intent(MainActivity.this, adminMain.class));
            Toast.makeText(MainActivity.this,"Already Logged in, Please wait!",Toast.LENGTH_SHORT).show();
            finish();
        }

        else if(auth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, Homepage.class));
            Toast.makeText(MainActivity.this,"Already Logged in, Please wait!",Toast.LENGTH_SHORT).show();
            finish();
        }

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, adminlog.class);
                startActivity(intent);
            }
        });
    }

    public void openSignin(View view) {
        Intent intent = new Intent(this, SigninPage.class);
        startActivity(intent);
    }

    public void openLogin(View view) {
        Intent intent1 = new Intent(this, LoginPage.class);
        startActivity(intent1);
    }

}