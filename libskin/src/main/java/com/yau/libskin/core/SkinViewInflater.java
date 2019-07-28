package com.yau.libskin.core;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatViewInflater;
import android.util.AttributeSet;
import android.view.View;

import com.yau.libskin.view.SkinButton;
import com.yau.libskin.view.SkinLinearLayout;
import com.yau.libskin.view.SkinTextView;

/**
 * author: yau
 * time: 2019/07/27 13:16
 * desc:
 */
public class SkinViewInflater extends AppCompatViewInflater {

    private Context context;

    public SkinViewInflater(Context context) {
        this.context = context;
    }

    @Nullable
    public View createView(String name, AttributeSet attrs) {
        View view = null;
        switch (name) {
            case "TextView":
                view = new SkinTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case "Button":
                view = new SkinButton(context, attrs);
                verifyNotNull(view, name);
                break;
            case "LinearLayout":
                view = new SkinLinearLayout(context, attrs);
                verifyNotNull(view, name);
                break;
        }
        return view;
    }

    /**
     * 校验控件不为空（源码方法，由于private修饰，只能复制过来了。为了代码健壮，可有可无）
     *
     * @param view 被校验控件，如：AppCompatTextView extends TextView（v7兼容包，兼容是重点！！！）
     * @param name 控件名，如："ImageView"
     */
    private void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(this.getClass().getName() + " asked to inflate view for <" + name + ">, but returned null");
        }
    }
}
