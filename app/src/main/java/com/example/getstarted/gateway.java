package com.example.getstarted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.getstarted.Models.CartModel;
import com.example.getstarted.activities.Homepage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class gateway extends AppCompatActivity implements PaymentResultListener {

    FirebaseAuth auth;
    FirebaseFirestore db;
    Button back,buynow;
    private Context context;
    final HashMap<String, Object> cartMap = new HashMap<>();
    int amount;
    String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway);
        Intent intent1 = getIntent();
        total = intent1.getStringExtra("total");

        back = findViewById(R.id.walletoprofi);
        buynow = findViewById(R.id.buynow);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        List<CartModel> list = (ArrayList<CartModel>) getIntent().getSerializableExtra("itemlist");
        for(CartModel model : list) {
            cartMap.put("productName", model.getProductName());
            cartMap.put("productPrice", model.getProductPrice());
            cartMap.put("currentDate", model.getCurrentDate());
            cartMap.put("currentTime", model.getCurrentTime());
            cartMap.put("totalQuantity", model.getTotalQuantity());
            cartMap.put("totalPrice", model.getTotalPrice());
            cartMap.put("img_url", model.getImg_url());
            amount = Math.round(Float.parseFloat(total) * 100);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gateway.this, Homepage.class);
                startActivity(intent);
            }
        });

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_PoUvIhLHBU5IkW");
                checkout.setImage(R.mipmap.ic_luncher_round);

                JSONObject object = new JSONObject();
                try {
                    object.put("name","Smart Shopping");
                    object.put("description","Order Payment gateway");
                    object.put("theme.color",R.color.purple_200);
                    object.put("amount",amount);
                    object.put("prefill.name","User");
                    object.put("prefill.email","gateway@gmail.com");
                    checkout.open(gateway.this,object);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


                if(list != null && list.size() > 0){
                    for(CartModel model : list){
                        final HashMap<String,Object> cartMap = new HashMap<>();
                        cartMap.put("productName",model.getProductName());
                        cartMap.put("productPrice",model.getProductPrice());
                        cartMap.put("currentDate",model.getCurrentDate());
                        cartMap.put("currentTime",model.getCurrentTime());
                        cartMap.put("totalQuantity",model.getTotalQuantity());
                        cartMap.put("totalPrice",model.getTotalPrice());
                        cartMap.put("img_url", model.getImg_url());
                        int total = model.getTotalPrice();
                        amount = Math.round(total*100);
                    }
                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();
        List<CartModel> list = (ArrayList<CartModel>) getIntent().getSerializableExtra("itemlist");
        for(CartModel model : list) {
            cartMap.put("productName", model.getProductName());
            cartMap.put("productPrice", model.getProductPrice());
            cartMap.put("currentDate", model.getCurrentDate());
            cartMap.put("currentTime", model.getCurrentTime());
            cartMap.put("totalQuantity", model.getTotalQuantity());
            cartMap.put("totalPrice", model.getTotalPrice());
            cartMap.put("img_url", model.getImg_url());
            int total = model.getTotalPrice();
            amount = Math.round(total * 100);
            db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                    .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                    Toast.makeText(gateway.this, "Order Placed!", Toast.LENGTH_SHORT).show();


                    db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("addtoCart")
                            .document(model.getDocumentId())
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        list.remove(model);
                                        notifyAll();
                                        Toast.makeText(context, "Item Deleted!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Error!" + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

    }
}