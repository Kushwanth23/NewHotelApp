package com.example.newhotelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.newhotelapp.Model.Hotels;
import com.example.newhotelapp.Model.UserModel;
import com.example.newhotelapp.databinding.ActivityDetailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.example.newhotelapp.Adapters.ItemAdapter.hotelsList;
public class DetailActivity extends AppCompatActivity {

    public static final String TAG="Database";

    ActivityDetailBinding binding;

    Hotels model;

    int position;

    DatabaseReference reference;
    FirebaseUser user;

    String email,username,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        position = getIntent().getIntExtra("pos",0);

        model = hotelsList.get(position);

        if (model !=null){



            if(model.getAvailable()){
                binding.txtSold.setVisibility(View.GONE);
                binding.btnPurchase.setVisibility(View.VISIBLE);


            }
            binding.name.setText(model.getName());
            binding.price.setText("₹"+model.getPrice()+".0");
            try{
                Picasso.get().load(model.getImage()).placeholder(R.drawable.hotel_icon)
                        .into(binding.productImage);
            }catch (Exception e){
                e.getMessage();
            }
            binding.description.setText(model.getDescription());

            binding.category.setText(model.getCategory());

        }

        getUserData();
    }

    private void getUserData(){
        reference.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    UserModel model = snapshot.getValue(UserModel.class);
                    if (model !=null){
                        email = model.getEmil();
                        phone = model.getPhone();
                        username = model.getUsername();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailActivity.this, "Error: "+
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}