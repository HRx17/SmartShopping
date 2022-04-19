package com.example.getstarted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.getstarted.Models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class description extends AppCompatActivity {

    ImageView imageView,add,remove;
    TextView price,rating,des,cart;
    Button button;
    int total=0;
    int totalprice=1;
    ViewAllModel viewAllModel = null;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        final Object object = getIntent().getSerializableExtra("details");
        if(object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel) object;
        }

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.detailimg);
        add = findViewById(R.id.addtocart);
        remove = findViewById(R.id.removefromcart);
        price = findViewById(R.id.price);
        rating = findViewById(R.id.rating);
        des = findViewById(R.id.description);
        cart = findViewById(R.id.addcartview1);
        button = findViewById(R.id.addcart);

        if(viewAllModel != null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(imageView);
            rating.setText(viewAllModel.getRating());
            des.setText(viewAllModel.getDescription());
            price.setText(viewAllModel.getPrice()+"/Kg");
            totalprice = viewAllModel.getPrice() * total;
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(total < 10){
                    total++;
                    cart.setText(String.valueOf(total));
                    totalprice = viewAllModel.getPrice() * total;
                }
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(total > 0){
                    total--;
                    cart.setText(String.valueOf(total));
                    totalprice = viewAllModel.getPrice() * total;
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(total != 0 ){
                    addedtocart();
                }
            }
        });
    }

    private void addedtocart() {
        String savecurrentDate,savecurrentTime;
        Calendar calforDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
        savecurrentDate = currentDate.format(calforDate.getTime());

        SimpleDateFormat currentT = new SimpleDateFormat("HH:mm:ss a");
        savecurrentTime = currentT.format(calforDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productName",viewAllModel.getName());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentDate",savecurrentDate);
        cartMap.put("currentTime",savecurrentTime);
        cartMap.put("totalQuantity",cart.getText().toString());
        cartMap.put("totalPrice",totalprice);
        cartMap.put("img_url", imageView.getDrawable().toString());

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("addtoCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                Toast.makeText(description.this, "Added to Cart!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}