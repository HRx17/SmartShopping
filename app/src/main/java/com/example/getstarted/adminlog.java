package com.example.getstarted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class adminlog extends AppCompatActivity {

    EditText ausern;
    EditText apass,tokn;
    Button alogin;
    FirebaseAuth auth;
    public String token = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlog);
        auth = FirebaseAuth.getInstance();
        tokn = findViewById(R.id.token);
        ausern = findViewById(R.id.adminuser);
        apass = findViewById(R.id.adminPassword);
        alogin = findViewById(R.id.adminlogin);

        alogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputuser = ausern.getText().toString();
                String inputpass = apass.getText().toString();
                token = tokn.getText().toString();
                if (inputuser.isEmpty() || inputpass.isEmpty()|| token.isEmpty()) {
                    Toast.makeText(adminlog.this, "Please Enter All The Details!", Toast.LENGTH_SHORT).show();
                }
                else if(token.equals("20216")){

                    auth.signInWithEmailAndPassword(inputuser,inputpass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(adminlog.this,"Log-In Successfull!",Toast.LENGTH_SHORT).show();;
                                        Intent logMain = new Intent(adminlog.this, adminMain.class);
                                        startActivity(logMain);
                                        SharedPreferences sharedPreferences = getSharedPreferences("token", 0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("token", tokn.getText().toString());
                                        editor.apply();
                                    }
                                    else{
                                        Toast.makeText(adminlog.this,"Error:"+task.isSuccessful(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(adminlog.this, "Invalid Token!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}