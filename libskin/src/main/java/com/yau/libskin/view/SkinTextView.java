package com.yau.libskin.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.yau.libskin.R;
import com.yau.libskin.base.ISkinView;
import com.yau.libskin.bean.AttrBean;

/**
 * author: yau
 * time: 2019/07/27 12:45
 * desc:
 */
public class SkinTextView extends AppCompatTextView implements ISkinView {

    private AttrBean mAttrBean;

    public SkinTextView(Context context) {
        this(context, null);
    }

    public SkinTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.SkinTextView, defStyleAttr, 0);
        mAttrBean = new AttrBean(typedArray, R.styleable.SkinTextView);
        typedArray.recycle();
    }

    @Override
    public void onSkinChange() {
        int textColorResId = mAttrBean.getResById(
                R.styleable.SkinTextView[R.styleable.SkinTextView_android_textColor]);
        if (textColorResId > 0) {
            ColorStateList textColorStateList =
                    ContextCompat.getColorStateList(getContext(), textColorResId);
            setTextColor(textColorStateList);
        }

        int backgroundResId =
                mAttrBean.getResById(R.styleable.SkinTextView[R.styleable.SkinTextView_android_background]);
        if (backgroundResId > 0) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), backgroundResId);
            setBackgroundDrawable(drawable);
        }
    }
}
