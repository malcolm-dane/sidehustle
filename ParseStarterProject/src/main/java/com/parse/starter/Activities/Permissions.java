package com.parse.starter.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.parse.starter.R;


import java.util.HashMap;

public class Permissions extends AppCompatActivity {
    public static final String MainPP_SP = "MainPP_data";
    private static final int REQUEST = 112;
    public static final int R_PERM = 2822;
    Context mContext = this;
    String[] permissions = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_NETWORK_STATE", "android.permission.INTERNET", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap<String, String> map = (HashMap) getSharedPreferences(MainPP_SP, 0).getAll();
        if (VERSION.SDK_INT >= 23) {
            Log.d("TAG", "@@@ IN IF Build.VERSION.SDK_INT >= 23");
            String[] PERMISSIONS = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.ACCESS_FINE_LOCATION", "android.permission.CAPTURE_VIDEO_OUTPUT", "android.permission.RECORD_AUDIO", "android.permission.READ_SMS", "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW"};
            if (hasPermissions(this.mContext, PERMISSIONS)) {
                Log.d("TAG", "@@@ IN ELSE hasPermissions");
                callNextActivity();
                return;
            }
            Log.d("TAG", "@@@ IN IF hasPermissions");
            ActivityCompat.requestPermissions((Activity) this.mContext, PERMISSIONS, 112);
            return;
        }
        Log.d("TAG", "@@@ IN ELSE  Build.VERSION.SDK_INT >= 23");
        callNextActivity();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 112:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Log.d("TAG", "@@@ PERMISSIONS Denied");
                    callNextActivity();
                    return;
                }
                Log.d("TAG", "@@@ PERMISSIONS grant");
                callNextActivity();
                return;
            default:
                return;
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (!(VERSION.SDK_INT < 23 || context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void callNextActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
