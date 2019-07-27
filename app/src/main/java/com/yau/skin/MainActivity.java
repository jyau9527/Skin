package com.yau.skin;

import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.yau.libskin.SkinActivity;

public class MainActivity extends SkinActivity {

    private boolean isNight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switchSkin(View view) {
        if (isNight) {
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            isNight = false;
        } else {
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            isNight = true;
        }
    }
}
