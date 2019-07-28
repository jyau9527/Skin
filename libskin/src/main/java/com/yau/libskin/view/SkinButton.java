package com.yau.libskin.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.yau.libskin.R;
import com.yau.libskin.SkinManager;
import com.yau.libskin.bean.AttrBean;
import com.yau.libskin.core.ISkinView;

/**
 * author: yau
 * time: 2019/07/27 12:45
 * desc:
 */
public class SkinButton extends AppCompatButton implements ISkinView {

    private AttrBean mAttrBean;

    public SkinButton(Context context) {
        this(context, null);
    }

    public SkinButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.SkinButton, defStyleAttr, 0);
        mAttrBean = new AttrBean(typedArray, R.styleable.SkinButton);
        typedArray.recycle();
    }

    @Override
    public void onSkinChange() {
        int textColorResId = mAttrBean.getResById(
                R.styleable.SkinButton[R.styleable.SkinButton_android_textColor]);
        if (textColorResId > 0) {
            ColorStateList textColorStateList;
            if (SkinManager.getInstance().isUseDefaultSkin()) {
                textColorStateList = ContextCompat.getColorStateList(getContext(), textColorResId);
            } else {
                textColorStateList = SkinManager.getInstance().getColorStateList(textColorResId);
            }
            setTextColor(textColorStateList);
        }

        int backgroundResId =
                mAttrBean.getResById(R.styleable.SkinButton[R.styleable.SkinButton_android_background]);
        if (backgroundResId > 0) {
            Drawable drawable;
            if (SkinManager.getInstance().isUseDefaultSkin()) {
                drawable = ContextCompat.getDrawable(getContext(), backgroundResId);
            } else {
                drawable = SkinManager.getInstance().getDrawableOrMipMap(backgroundResId);
            }
            setBackgroundDrawable(drawable);
        }

        int typefaceResId =
                mAttrBean.getResById(R.styleable.SkinButton[R.styleable.SkinButton_customTypeFace]);
        if (typefaceResId > 0) {
            if (SkinManager.getInstance().isUseDefaultSkin()) {
                setTypeface(Typeface.DEFAULT);
            } else {
                Typeface typeface = SkinManager.getInstance().getTypeface(typefaceResId);
                setTypeface(typeface);
            }
        }
    }
}
