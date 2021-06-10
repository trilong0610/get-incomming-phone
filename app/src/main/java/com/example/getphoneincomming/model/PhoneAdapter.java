package com.example.getphoneincomming.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getphoneincomming.R;

import java.util.ArrayList;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneItemViewHolder> {
    private ArrayList<String> phones;
    private Context context;

    public PhoneAdapter(ArrayList<String> phones, Context context) {
        this.phones = phones;
        this.context = context;
    }

    @NonNull
    @Override
    public PhoneItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phone, parent, false);
        parent.scrollTo(0, 0);
        return new PhoneItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneItemViewHolder holder, int position) {
        String phone = phones.get(position);
        holder.tvPhone.setText(phone);
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }
    public class PhoneItemViewHolder extends RecyclerView.ViewHolder{

        private TextView tvPhone;

        public PhoneItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPhone = itemView.findViewById(R.id.tv_item_phone_number_phone);
        }
    }
}
