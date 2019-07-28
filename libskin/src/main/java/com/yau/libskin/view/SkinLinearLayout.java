package com.yau.libskin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yau.libskin.R;
import com.yau.libskin.SkinManager;
import com.yau.libskin.core.ISkinView;
import com.yau.libskin.bean.AttrBean;

/**
 * author: yau
 * time: 2019/07/27 12:45
 * desc:
 */
public class SkinLinearLayout extends LinearLayout implements ISkinView {

    private AttrBean mAttrBean;

    public SkinLinearLayout(Context context) {
        this(context, null);
    }

    public SkinLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.SkinLinearLayout, defStyleAttr, 0);
        mAttrBean = new AttrBean(typedArray, R.styleable.SkinLinearLayout);
        typedArray.recycle();
    }

    @Override
    public void onSkinChange() {
        int backgroundResId =
                mAttrBean.getResById(R.styleable.SkinLinearLayout[R.styleable.SkinLinearLayout_android_background]);
        if (backgroundResId > 0) {
            Drawable drawable;
            if (SkinManager.getInstance().isUseDefaultSkin()) {
                drawable = ContextCompat.getDrawable(getContext(), backgroundResId);
            } else {
                drawable = SkinManager.getInstance().getDrawableOrMipMap(backgroundResId);
            }
            setBackground(drawable);
        }
    }
}
