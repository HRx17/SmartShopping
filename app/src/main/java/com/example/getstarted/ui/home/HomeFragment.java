package com.example.getstarted.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getstarted.Models.CategoryModel;
import com.example.getstarted.Models.Recommended;
import com.example.getstarted.R;
import com.example.getstarted.Models.PopularModel;
import com.example.getstarted.activities.ViewAllActivity;
import com.example.getstarted.adaptors.CategoryAdaptor;
import com.example.getstarted.adaptors.PopularAdaptor;
import com.example.getstarted.adaptors.RecommendedAdaptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView popularRec,catRec,recomRec;
    FirebaseFirestore db;

    TextView viewp,viewr;
    List<PopularModel> popularModelList;
    List<CategoryModel> CategoryList;
    List<Recommended> recommendedList;
    PopularAdaptor popularAdaptor;
    CategoryAdaptor categoryAdaptor;
    RecommendedAdaptor recommendedAdaptor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();

        View root = inflater.inflate(R.layout.fragment_home,container,false);

        viewp = root.findViewById(R.id.view_all_products);
        viewr = root.findViewById(R.id.view_all_recommended);
        popularRec = root.findViewById(R.id.pop_rec);
        catRec = root.findViewById(R.id.exp_rec);
        recomRec = root.findViewById(R.id.rec_rec);

        viewp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), ViewAllActivity.class);
                intent.putExtra("type","viewp");
                startActivity(intent);
            }
        });
        viewr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), ViewAllActivity.class);
                intent.putExtra("type","viewr");
                startActivity(intent);
            }
        });

        //popular
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList = new ArrayList<>();
        popularAdaptor = new PopularAdaptor(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdaptor);

        //popular
        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdaptor.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    });

        //cat
        catRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        CategoryList = new ArrayList<>();
        categoryAdaptor = new CategoryAdaptor(getActivity(),CategoryList);
        catRec.setAdapter(categoryAdaptor);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                CategoryList.add(categoryModel);
                                categoryAdaptor.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //recommended
        recomRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recommendedList = new ArrayList<>();
        recommendedAdaptor = new RecommendedAdaptor(getActivity(),recommendedList);
        recomRec.setAdapter(recommendedAdaptor);

        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Recommended recommended = document.toObject(Recommended.class);
                                recommendedList.add(recommended);
                                recommendedAdaptor.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;

    }
}