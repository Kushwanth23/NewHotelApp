package com.example.newhotelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.newhotelapp.Adapters.ItemAdapter;
import com.example.newhotelapp.Model.Hotels;
import com.example.newhotelapp.databinding.ActivityHotelListBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HotelListActivity extends AppCompatActivity {

    ActivityHotelListBinding binding;

    DatabaseReference reference;

    ArrayList<Hotels> list = new ArrayList<>();

    ItemAdapter adapter;

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        category = getIntent().getStringExtra("cat");

        reference = FirebaseDatabase.getInstance().getReference().child("Hotels");
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerView.setHasFixedSize(true);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getHotels();
            }
        },300);

    }

    private void getHotels(){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    binding.progressBar.setVisibility(View.GONE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Hotels hotels = dataSnapshot.getValue(Hotels.class);
                        if (hotels.getCategory().equals(category)){
                            list.add(hotels);
                        }
                    }

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(HotelListActivity.this,3);
                    binding.recyclerView.setLayoutManager(gridLayoutManager);

                    adapter = new ItemAdapter(HotelListActivity.this,list);
                    binding.recyclerView.setAdapter(adapter);
                }else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(HotelListActivity.this, "No products now!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(HotelListActivity.this, "Error: "+
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}