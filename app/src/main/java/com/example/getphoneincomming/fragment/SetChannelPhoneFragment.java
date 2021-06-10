package com.example.getphoneincomming.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getphoneincomming.R;
import com.example.getphoneincomming.model.PhoneAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class SetChannelPhoneFragment extends Fragment {
    public static Socket mSocket;

    private ArrayList<String> phones;
    private PhoneAdapter adapter;
    private RecyclerView rvPhone;
    private MaterialButton btnTest;
    private TextInputEditText edtPhone;

    {
        try {
            mSocket = IO.socket("http://10.0.2.2:3000");
        } catch (URISyntaxException e) {}
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocket.connect();
        mSocket.on("new message", onNewMessage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_channel_phone, container, false);

        Mapping(view);

        return view;
    }

    private void Mapping(View view) {

        phones = new ArrayList<>();
        rvPhone = view.findViewById(R.id.rv_set_phone_phone);
        btnTest = view.findViewById(R.id.btn_set_phone_test);
        edtPhone = view.findViewById(R.id.edt_set_phone_phone);
        phones.add(String.valueOf(1));

//     ------------------SOCKET.IO-------------------



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPhone.setLayoutManager(layoutManager);
        adapter = new PhoneAdapter(phones,getContext());
        rvPhone.setAdapter(adapter);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend(edtPhone.getText().toString().trim());
            }
        });

    }

//    Send to server socket
    private void attemptSend(String text){
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast.makeText(getContext(), "Send" + edtPhone.getText().toString().trim(), Toast.LENGTH_SHORT).show();
        mSocket.emit("new message", text);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();

    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = (String) args[0];
                    phones.add(data);
                    adapter.notifyDataSetChanged();

                }
            });
        }
    };
}