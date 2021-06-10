package com.example.getphoneincomming.activity;

import android.Manifest;
import android.app.Activity;
import android.app.role.RoleManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.getphoneincomming.R;
import com.example.getphoneincomming.fragment.SetChannelPhoneFragment;

public class MainActivity extends AppCompatActivity {
    private static final int CHANGE_DEFAULT_DIALER_CODE = 1;
    private static final int PERMISSION_REQUEST_READ_PHONE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPhonePermission();

        //    lay quyen lam trinh goi/sms mac dinh > 10
        //    Vi phai co quyen nay broadcast nhan sdt mơi chay khi app bị kill
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            RoleManager rolemanager = getApplicationContext().getSystemService(RoleManager.class);
            if (rolemanager.isRoleAvailable(RoleManager.ROLE_DIALER)){
//                Kiem tra xem app da co quyen ROLE DIALER chua
//                Neu chua co thi tien hanh lay quyen
                if (!rolemanager.isRoleHeld(RoleManager.ROLE_DIALER)){
                    Intent roleRequestIntent = rolemanager.createRequestRoleIntent(RoleManager.ROLE_DIALER);
                    startActivityForResult(roleRequestIntent,CHANGE_DEFAULT_DIALER_CODE);
                }
            }
            Toast.makeText(this, "Đang lấy quyền DEFAULT_DIALER_ROLE", Toast.LENGTH_SHORT).show();
        }
        //    lay quyen lam trinh goi/sms mac dinh < 10
        else{

            TelecomManager telecomManager = (TelecomManager) getSystemService(TELECOM_SERVICE);
            if (!getPackageName().equals(telecomManager.getDefaultDialerPackage())) {
                Intent intent = new Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                        .putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getPackageName());
                startActivity(intent);
            }
            Toast.makeText(this, "Đang lấy quyền DEFAULT_DIALER_TELECOM", Toast.LENGTH_SHORT).show();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new SetChannelPhoneFragment()).commit();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHANGE_DEFAULT_DIALER_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Đã cấp quyền Telecom/Role", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Từ chối cấp quyền Telecom/Role", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_PHONE) {
            // Request for camera permission.
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Toast.makeText(MainActivity.this,"Đã cấp quyền phone",Toast.LENGTH_SHORT).show();
//                requestWriteStoragePermission();
            } else {
                // Permission request was denied.
                Toast.makeText(MainActivity.this,"Từ chối cấp quyền phone",Toast.LENGTH_LONG).show();
            }
        }
    }

    //    Cap quyen doc lich su cuoc goi cho android < 10
    public void checkPhonePermission() {
        // Check if the read Storage permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                        != PackageManager.PERMISSION_GRANTED) {
            // Permission is missing and must be requested.

            requestPhonePermission();
        }

    }

    public void requestPhonePermission() {
        // Permission has not been granted and must be requested.
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CALL_LOG}, PERMISSION_REQUEST_READ_PHONE);

    }
}