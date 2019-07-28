package com.yau.libskin;

import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.yau.libskin.bean.SkinCache;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * author: yau
 * time: 2019/07/28 22:55
 * desc:
 */
public class SkinManager {

    private Application application;
    private Resources appResources; // 用于加载app内置资源
    private Resources skinResources; // 用于加载皮肤包资源
    private String skinPackageName; // 皮肤包资源所在包名（注：皮肤包不在app内，也不限包名）
    private boolean useDefaultSkin = true; // 应用默认皮肤（app内置）
    private Map<String, SkinCache> cacheSkinMap;

    private static final class SingletonHolder {
        private static final SkinManager INSTANCE = new SkinManager();
    }

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void init(Application application) {
        this.application = application;
        appResources = application.getResources();
        cacheSkinMap = new ArrayMap<>();
    }

    /**
     * 加载皮肤包资源
     *
     * @param skinPath 皮肤包路径，为空则加载app内置资源
     */
    public void loadSkin(String skinPath) {
        if (TextUtils.isEmpty(skinPath)) {
            useDefaultSkin = true;
            return;
        }

        if (cacheSkinMap.containsKey(skinPath)) {
            useDefaultSkin = false;
            SkinCache skinCache = cacheSkinMap.get(skinPath);
            if (skinCache != null) {
                skinResources = skinCache.getResources();
                skinPackageName = skinCache.getPackageName();
                return;
            }
        }

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPath);

            skinResources = new Resources(assetManager,
                    appResources.getDisplayMetrics(), appResources.getConfiguration());
            skinPackageName = application.getPackageManager()
                    .getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;

            useDefaultSkin = TextUtils.isEmpty(skinPackageName);
            if (!useDefaultSkin) {
                cacheSkinMap.put(skinPath, new SkinCache(skinResources, skinPackageName));
            }
        } catch (Exception e) {
            e.printStackTrace();
            useDefaultSkin = true;
        }
    }

    /**
     * 参考：resources.arsc资源映射表
     * 通过ID值获取资源 Name 和 Type
     *
     * @param resId 资源ID值
     * @return 如果没有皮肤包则加载app内置资源ID，反之加载皮肤包指定资源ID
     */
    private int getSkinResourceId(int resId) {
        if (useDefaultSkin) return resId;

        String resName = appResources.getResourceEntryName(resId);
        String resType = appResources.getResourceTypeName(resId);

        int skinResId = skinResources.getIdentifier(resName, resType, skinPackageName);
        useDefaultSkin = skinResId == 0;
        return skinResId == 0 ? resId : skinResId;
    }

    public boolean isUseDefaultSkin() {
        return useDefaultSkin;
    }

    //==============================================================================================

    public int getColor(int resourceId) {
        int ids = getSkinResourceId(resourceId);
        return useDefaultSkin ? appResources.getColor(ids) : skinResources.getColor(ids);
    }

    public ColorStateList getColorStateList(int resourceId) {
        int ids = getSkinResourceId(resourceId);
        return useDefaultSkin ? appResources.getColorStateList(ids) : skinResources.getColorStateList(ids);
    }

    // mipmap和drawable统一用法（待测）
    public Drawable getDrawableOrMipMap(int resourceId) {
        int ids = getSkinResourceId(resourceId);
        return useDefaultSkin ? appResources.getDrawable(ids) : skinResources.getDrawable(ids);
    }

    public String getString(int resourceId) {
        int ids = getSkinResourceId(resourceId);
        return useDefaultSkin ? appResources.getString(ids) : skinResources.getString(ids);
    }

    // 返回值特殊情况：可能是color / drawable / mipmap
    public Object getBackgroundOrSrc(int resourceId) {
        // 需要获取当前属性的类型名Resources.getResourceTypeName(resourceId)再判断
        String resourceTypeName = appResources.getResourceTypeName(resourceId);

        switch (resourceTypeName) {
            case "color":
                return getColor(resourceId);

            case "mipmap": // drawable / mipmap
            case "drawable":
                return getDrawableOrMipMap(resourceId);
        }
        return null;
    }

    // 获得字体
    public Typeface getTypeface(int resourceId) {
        // 通过资源ID获取资源path，参考：resources.arsc资源映射表
        String skinTypefacePath = getString(resourceId);
        // 路径为空，使用系统默认字体
        if (TextUtils.isEmpty(skinTypefacePath)) return Typeface.DEFAULT;
        return useDefaultSkin ? Typeface.createFromAsset(appResources.getAssets(), skinTypefacePath)
                : Typeface.createFromAsset(skinResources.getAssets(), skinTypefacePath);
    }
}
