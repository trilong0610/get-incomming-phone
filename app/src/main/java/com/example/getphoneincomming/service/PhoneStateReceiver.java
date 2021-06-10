package com.example.getphoneincomming.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.getphoneincomming.fragment.SetChannelPhoneFragment;

public class PhoneStateReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING) && incomingNumber != null){
                Toast.makeText(context,"Ringing State Number is - " + incomingNumber, Toast.LENGTH_SHORT).show();

                SetChannelPhoneFragment.mSocket.emit("new message",incomingNumber);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
