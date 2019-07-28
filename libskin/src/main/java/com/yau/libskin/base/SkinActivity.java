package com.yau.libskin.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yau.libskin.SkinManager;
import com.yau.libskin.core.ISkinView;
import com.yau.libskin.utils.ActionBarUtils;
import com.yau.libskin.utils.NavigationUtils;
import com.yau.libskin.utils.StatusBarUtils;
import com.yau.libskin.core.SkinViewInflater;

/**
 * author: yau
 * time: 2019/07/27 12:42
 * desc:
 */
public abstract class SkinActivity extends AppCompatActivity {

    private SkinViewInflater mSkinViewInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(inflater, this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if (mSkinViewInflater == null) {
            mSkinViewInflater = new SkinViewInflater(this);
        }
        View view = mSkinViewInflater.createView(name, attrs);
        return view == null ? super.onCreateView(parent, name, context, attrs) : view;
    }

    protected void defaultSkin(int themeColorId) {
        this.switchSkin(null, themeColorId);
    }

    protected void switchSkin(String skinPath, int themeColorId) {
        SkinManager.getInstance().loadSkin(skinPath);

        if (Build.VERSION.SDK_INT >= 21) {
            int color = SkinManager.getInstance().getColor(themeColorId);
            StatusBarUtils.forStatusBar(this, color);
            ActionBarUtils.forActionBar(this, color);
            NavigationUtils.forNavigation(this, color);
        }

        notifySkinChange(getWindow().getDecorView());
    }

    protected void setDayNightMode(@AppCompatDelegate.NightMode int nightMode) {
        getDelegate().setLocalNightMode(nightMode);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.forStatusBar(this);
            ActionBarUtils.forActionBar(this);
            NavigationUtils.forNavigation(this);
        }

        notifySkinChange(getWindow().getDecorView());
    }

    private void notifySkinChange(View view) {
        if (view instanceof ISkinView) {
            ISkinView skinView = (ISkinView) view;
            skinView.onSkinChange();
        }

        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                notifySkinChange(parent.getChildAt(i));
            }
        }
    }
}
