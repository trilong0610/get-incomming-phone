package com.example.getphoneincomming.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.getphoneincomming.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class HomeFragment extends Fragment {

    private SwitchMaterial switcher;
    private Intent intentPhone;
    private Intent intentPhone9;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Mapping(view);

        MappingEvent(view);
//       Lấy số điện thoại của máy hiện tại
//       Gửi số điện thoại lên server để PC chọn

//       Lấy thông tin sđt của cuộc gọi đến

//        Thiết lập socketio

        return view;
    }



    private void Mapping(View view) {
        switcher = view.findViewById(R.id.switcher_home_activity);
    }

    private void MappingEvent(View view){
    }
}