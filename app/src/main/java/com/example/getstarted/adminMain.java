package com.example.getstarted;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.getstarted.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import id.zelory.compressor.Compressor;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class adminMain extends AppCompatActivity {

    ImageView imageView;
    EditText name,des,rating,price,type;
    Button button, logout;
    private Uri imageUri = null;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    final int id = 0211;
    private String user_id;
    private Bitmap compressed;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        logout = findViewById(R.id.admin_out);
        name = findViewById(R.id.product_name);
        des = findViewById(R.id.product_des);
        rating = findViewById(R.id.product_rating);
        price = findViewById(R.id.product_price);
        type = findViewById(R.id.product_type);
        imageView = findViewById(R.id.product_img);
        button = findViewById(R.id.product_add);
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(adminMain.this, MainActivity.class);
                startActivity(intent);
                SharedPreferences sharedPreferences = getSharedPreferences("token", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", "0");
                editor.apply();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(adminMain.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(adminMain.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(adminMain.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                    else {
                        choseImage();
                    }
                }
                else {
                    choseImage();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String pname = name.getText().toString();
                final String pdes = des.getText().toString();
                final String prating = rating.getText().toString();
                final String pricee = price.getText().toString();
                final String ptype = type.getText().toString();
                final String img_url = imageView.toString();

                if(!TextUtils.isEmpty(pname) && !TextUtils.isEmpty(pdes) && !TextUtils.isEmpty(prating) && imageUri!=null && !TextUtils.isEmpty(ptype) && !TextUtils.isEmpty(pricee) && !TextUtils.isEmpty(img_url)){
                    File newFile = new File(imageUri.getPath());
                    try {
                        compressed = new Compressor(adminMain.this)
                                .setMaxHeight(125)
                                .setMaxWidth(125)
                                .setQuality(50)
                                .compressToBitmap(newFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    compressed.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] thumbData = byteArrayOutputStream.toByteArray();

                    UploadTask image_path = storageReference.child("user_image").child(user_id + ".jpg").putBytes(thumbData);
                    image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                int price = Integer.parseInt(pricee);
                                storeData(task, pname, pdes, prating,ptype,price);
                            }
                            else {
                                String error = task.getException().getMessage();
                                Toast.makeText(adminMain.this, "(IMAGE Error) : " + error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(adminMain.this, "Please Fill All Details!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void storeData(Task<UploadTask.TaskSnapshot> task, String namee, String description, String ratingg, String typee, int pricee) {
        Uri download_uri;

        if (task != null) {


            download_uri = task.getResult().getUploadSessionUri();

        } else {

            download_uri = imageUri;

        }

        Map<String, Object> userData = new HashMap<>();
        userData.put("name",namee);
        userData.put("description",description);
        userData.put("rating",ratingg);
        userData.put("type",typee);
        userData.put("price",pricee);
        userData.put("img_url",download_uri.toString());

        firebaseFirestore.collection("AllProducts").add(userData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                if (task.isSuccessful()) {
                    Toast.makeText(adminMain.this, "User Data is Stored Successfully", Toast.LENGTH_LONG).show();

                } else {

                    String error = task.getException().getMessage();
                    Toast.makeText(adminMain.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
                }
            }
       /* })Listener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(adminMain.this, "User Data is Stored Successfully", Toast.LENGTH_LONG).show();

                } else {

                    String error = task.getException().getMessage();
                    Toast.makeText(adminMain.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
                }
            }*/
        });
    }

    private void choseImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(adminMain.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();
                imageView.setImageURI(imageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }
    }
}