package com.example.getstarted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LoginPage extends AppCompatActivity {

    private EditText eusern;
    private EditText epass;
    TextView signinn;
    private Button elogin;
    private Button skipM;
    FirebaseAuth auth;
    ProgressBar proges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_page);

        auth = FirebaseAuth.getInstance();
        proges = findViewById(R.id.prog2);

        eusern = findViewById(R.id.userIdL);
        epass = findViewById(R.id.userPassword);
        elogin = findViewById(R.id.logInMain);
        skipM = findViewById(R.id.skipTomain);
        signinn = findViewById(R.id.tosignin);

        elogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
                proges.setVisibility(View.VISIBLE);

                }
        });
        skipM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1){
                Intent skipMain = new Intent(LoginPage.this, Intro.class);
                startActivity(skipMain);
            }
        });
        signinn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this,SigninPage.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String inputuser = eusern.getText().toString();
        String inputpass = epass.getText().toString();
        if (inputuser.isEmpty() || inputpass.isEmpty()) {
            Toast.makeText(LoginPage.this, "Please Enter All The Details!", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(inputuser, inputpass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginPage.this, "Log-In Successfull!", Toast.LENGTH_SHORT).show();
                                ;
                                proges.setVisibility(View.GONE);
                                Intent logMain = new Intent(LoginPage.this, Hello.class);
                                startActivity(logMain);
                            } else {
                                proges.setVisibility(View.GONE);
                                Toast.makeText(LoginPage.this, "Error:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}