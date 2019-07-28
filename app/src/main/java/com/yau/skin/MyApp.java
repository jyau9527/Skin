package com.yau.skin;

import android.app.Application;

import com.yau.libskin.SkinManager;

/**
 * author: yau
 * time: 2019/07/28 23:09
 * desc:
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
