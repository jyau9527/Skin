package com.yau.skin;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.yau.libskin.base.SkinActivity;

import java.io.File;

public class MainActivity extends SkinActivity {

    public static final String TAG = "Skin >>>>>> ";

//    private boolean isNight = false;
    private boolean isSwitch = false;
    private String skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "test.skin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switchSkin(View view) {
//        if (isNight) {
//            isNight = false;
//            setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        } else {
//            isNight = true;
//            setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        }
        long startTime = System.currentTimeMillis();
        if (isSwitch) {
            isSwitch = false;
            Log.e(TAG, "switchSkin: toDefault ==> start");
            defaultSkin(R.color.colorPrimary);
        } else {
            isSwitch = true;
            Log.e(TAG, "switchSkin: toSwitch ==> start");
            switchSkin(skinPath, R.color.colorPrimary);
        }
        Log.e(TAG, "switchSkin ==> end: " + (System.currentTimeMillis() - startTime));
    }
}
