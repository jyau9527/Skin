package com.yau.libskin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.yau.libskin.view.SkinViewInflater;

/**
 * author: yau
 * time: 2019/07/27 12:42
 * desc:
 */
public abstract class SkinActivity extends AppCompatActivity {

    private SkinViewInflater mSkinViewInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater.from(this).setFactory2(this);
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
}
