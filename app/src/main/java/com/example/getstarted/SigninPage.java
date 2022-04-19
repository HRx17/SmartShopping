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

import com.example.getstarted.Models.Usermodels;
import com.example.getstarted.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class SigninPage extends AppCompatActivity {

    private Button signin;
    private EditText name,email,pass;
    TextView loginn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signin_page);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progress = findViewById(R.id.prog1);
        progress.setVisibility(View.GONE);

         signin = findViewById(R.id.logInMain);
         name = findViewById(R.id.editTextNumber);
         email = findViewById(R.id.userIdL);
         pass = findViewById(R.id.userPassword);
         loginn = findViewById(R.id.tosignin);
         loginn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(SigninPage.this,LoginPage.class);
                 startActivity(intent);
             }
         });
         signin.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v2){
                 signupUser();
                 progress.setVisibility(View.VISIBLE);

                 }
         });
        }

    private void signupUser() {
        String newUserp = name.getText().toString();
        String newemail = email.getText().toString();
        String newpass = pass.getText().toString();
        if(newUserp.isEmpty() || newemail.isEmpty() || newpass.isEmpty()){
            Toast.makeText(SigninPage.this, "Please Fill all the Details!", Toast.LENGTH_LONG).show();
        }
        else {
            auth.createUserWithEmailAndPassword(newemail, newpass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Usermodels userModel = new Usermodels(newUserp, newemail, newpass);
                                String id = task.getResult().getUser().getUid();
                                database.getReference().child("Admin").child(id).setValue(userModel);
                                progress.setVisibility(View.GONE);
                                Toast.makeText(SigninPage.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                Intent backtostart = new Intent(SigninPage.this, MainActivity.class);
                                startActivity(backtostart);
                            } else {
                                progress.setVisibility(View.GONE);
                                Toast.makeText(SigninPage.this, "Error:" + task.getException(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }
}