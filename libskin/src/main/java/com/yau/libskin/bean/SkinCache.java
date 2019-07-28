package com.yau.libskin.bean;

import android.content.res.Resources;

/**
 * author: yau
 * time: 2019/07/28 23:38
 * desc:
 */
public class SkinCache {

    private Resources skinResources;
    private String skinPackageName;

    public SkinCache(Resources skinResources, String skinPackageName) {
        this.skinResources = skinResources;
        this.skinPackageName = skinPackageName;
    }

    public Resources getResources() {
        return skinResources;
    }

    public String getPackageName() {
        return skinPackageName;
    }
}
