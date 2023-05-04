package com.example.newhotelapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newhotelapp.DetailActivity;
import com.example.newhotelapp.Model.Hotels;
import com.example.newhotelapp.R;
import com.example.newhotelapp.databinding.ItemProductBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    public static ArrayList<Hotels> hotelsList;

    ItemProductBinding binding;

    public ItemAdapter(Context context, ArrayList<Hotels> list) {
        this.context = context;
        this.hotelsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        binding = ItemProductBinding.inflate(LayoutInflater.from(context));

        return  new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hotels model = hotelsList.get(position);
        if (model != null) {
            binding.imgSold.setVisibility(View.GONE);
            if(model.getAvailable()){
                binding.imgSold.setVisibility(View.GONE);
            }else{
                binding.imgSold.setVisibility(View.VISIBLE);
            }
            binding.productTitle.setText(model.getName());
            binding.amount.setText("₹" + model.getPrice() + ".0 /Day");
            try {
                Picasso.get().load(model.getImage()).placeholder(R.drawable.hotel_icon)
                        .into(binding.productImage);
            } catch (Exception e) {
                e.getMessage();
            }

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("pos", position);
                context.startActivity(intent);
            });

        }
    }
    @Override
    public int getItemCount() {
        return hotelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;
        public ViewHolder(@NonNull ItemProductBinding productBinding) {
            super(productBinding.getRoot());
            binding = productBinding;
        }
    }

}
