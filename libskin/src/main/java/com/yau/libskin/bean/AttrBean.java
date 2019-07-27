package com.yau.libskin.bean;

import android.content.res.TypedArray;
import android.util.SparseIntArray;

/**
 * author: yau
 * time: 2019/07/27 12:49
 * desc:
 */
public class AttrBean {

    /**
     * 存储属性的resId
     */
    private SparseIntArray resMap = new SparseIntArray();

    /**
     * 存储控件的key、value
     *
     * @param typedArray 控件属性的类型集合，如：background / textColor
     * @param styleable  自定义属性，参考value/attrs.xml
     */
    public AttrBean(TypedArray typedArray, int[] styleable) {
        for (int i = 0; i < typedArray.length(); i++) {
            resMap.put(styleable[i],
                    typedArray.getResourceId(i, -1));
        }
    }

    /**
     * 获取控件某属性的resourceId
     *
     * @param styleable 自定义属性，参考value/attrs.xml
     * @return 某控件某属性值的resourceId
     */
    public int getResById(int styleable) {
        return resMap.get(styleable);
    }
}
