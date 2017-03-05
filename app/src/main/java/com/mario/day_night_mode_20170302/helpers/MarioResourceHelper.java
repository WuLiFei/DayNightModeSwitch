package com.mario.day_night_mode_20170302.helpers;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mario on 2017-02-08.
 */

public class MarioResourceHelper {

    private final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#00FFFFFF");
    private final int DEFAULT_HINT_TEXT_COLOR = Color.parseColor("#FF969696");
    private final int DEFAULT_TEXT_COLOR = Color.parseColor("#FF727272");
    private final int DEFAULT_TINT_COLOR = Color.parseColor("#00FFFFFF");
    private final int DEFAULT_COLOR = Color.parseColor("#FF000000");
    private final float DEFAULT_FLOAT_VALUE = 1.0f;

    private static MarioResourceHelper mInstance;  //  静态实例对象
    private Context mContext;  // 上下文环境

    private MarioResourceHelper(Context context) {
        setContext(context);
        // 私有化构造方法
    }

    /**
     * 初始化方法
     * @param context
     * @return MarioResourceHelper
     * */
    public static MarioResourceHelper getInstance(@NonNull Context context) {
        if (mInstance == null) {
            synchronized (MarioResourceHelper.class) {
                if (mInstance == null) mInstance = new MarioResourceHelper(context);
            }
        } else {
            mInstance.setContext(context);
        }
        return mInstance;
    }

    /**
     * 设置上下文环境
     * @param context  上下文环境
     * */
    private void setContext(Context context) {
        this.mContext = context;
    }

    /**
     * 获取上下文环境
     * @return Context
     * */
    private Context getContext() {
        return mContext;
    }

    /**
     * 检查上下文环境
     * */
    private boolean isCurrentContextValid() {
        if (getContext() == null) throw new NullPointerException(" the context could't be null. ");
        return getContext() != null;
    }

    /**
     * 设置当前上下文环境下的主题样式
     * @param resId  需要设置上去的主题样式所对应的资源Id
     * */
    public void setCurrentTheme(int resId) {
        if (!isCurrentContextValid()) return ;
        getContext().setTheme(resId);
    }

    /**
     * 设置Application的主题样式
     * @param resId  需要设置上去的主题样式所对应的资源Id
     * */
    public void setApplicationTheme(int resId) {
        if (!isCurrentContextValid()) return ;
        getContext().getApplicationContext().setTheme(resId);
    }

    /**
     * 获得当前上下文环境下对应的主题Theme
     * @return Theme
     * */
    public Resources.Theme getCurrentContextTheme() {
        if (!isCurrentContextValid()) return null;
        return getContext().getTheme();
    }

    /**
     * 获得Application所对应的上下文环境下的主题Theme
     * @return Theme
     * */
    public Resources.Theme getApplicationTheme() {
        if (!isCurrentContextValid()) return null;
        return getContext().getApplicationContext().getTheme();
    }

    /**
     * # 根据attr属性id获得typedValue对象
     * @param attrId  资源属性对应的id
     * @return TypedValue
     * */
    @Nullable
    private TypedValue obtainTypedValue(int attrId) {
        if (!isCurrentContextValid()) return null;
        Resources.Theme theme = getCurrentContextTheme();
        if (theme == null) return null;
        TypedValue typedValue = new TypedValue();
        try {
            theme.resolveAttribute(attrId, typedValue, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return typedValue;
        }
    }

    /**
     * 根据所给定的arrtId获得Identifier资源id
     * @param attrId
     * */
    public int getIdentifierByAttrId(int attrId) {
        TypedValue typedValue = obtainTypedValue(attrId);
        return typedValue == null ? 0 : typedValue.resourceId;
    }

    /**
     * # 根据给定的attrId获得typedArray属性集合
     * @param attrId
     * */
    @Nullable
    private TypedArray innerGetTypedArrayByAttr(int attrId) {
        if (!isCurrentContextValid()) return null;
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null) return null;
        TypedArray typedArray = null;
        try {
            typedArray = getContext().getResources().obtainTypedArray(typedValue.resourceId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return typedArray;
        }
    }

    /**
     * 根据给定的attrId获得typedArray属性集合
     * @param attrId
     * */
    private TypedArray getTypedArrayByAttr(int attrId) {
        return innerGetTypedArrayByAttr(attrId);
    }

    /**
     * # 根据给定的attrId获取Color颜色值
     * @param defaultColor
     * @param attrId
     * */
    private int innerGetColorByAttr(int attrId, int defaultColor) {
        if (!isCurrentContextValid()) return defaultColor;
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null) return defaultColor;
        try {
            defaultColor = ResourcesCompat.getColor(getContext().getResources(), typedValue.resourceId, getCurrentContextTheme());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return defaultColor;
        }
    }

    /**
     * 根据给定的attrId获取Color颜色值
     * @param defaultColor
     * @param attrId
     * */
    public int getColorByAttr(int attrId, int defaultColor) {
        return innerGetColorByAttr(attrId, defaultColor);
    }

    /**
     * 根据给定的attrId获取Color颜色值
     * @param attrId
     * */
    public int getColorByAttr(int attrId) {
        return innerGetColorByAttr(attrId, DEFAULT_COLOR);
    }

    /**
     * # 根据给定的attrId获取Drawable对象
     * @param defaultDrawable
     * @param attrId
     * */
    private Drawable innerGetDrawableByAttr(int attrId, Drawable defaultDrawable) {
        if (!isCurrentContextValid()) return defaultDrawable;
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null) return defaultDrawable;
        try {
            defaultDrawable = ResourcesCompat.getDrawable(getContext().getResources(), typedValue.resourceId, getCurrentContextTheme());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return defaultDrawable;
        }
    }

    /**
     * 根据给定的attrId获取Drawable对象
     * @param defaultDrawable
     * @param attrId
     * */
    public Drawable getDrawableByAttr(int attrId, Drawable defaultDrawable) {
        return innerGetDrawableByAttr(attrId, defaultDrawable);
    }

    /**
     * 根据给定的attrId获取Drawable对象
     * @param attrId
     * */
    public Drawable getDrawableByAttr(int attrId) {
        return innerGetDrawableByAttr(attrId, null);
    }

    /**
     * # 根据给定的attrId获取ColorStateList对象
     * @param defaultColorStateList
     * @param attrId
     * */
    private ColorStateList innerGetColorStateListByAttr(int attrId, ColorStateList defaultColorStateList) {
        if (!isCurrentContextValid()) return defaultColorStateList;
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null) return defaultColorStateList;
        try {
            defaultColorStateList = ResourcesCompat.getColorStateList(getContext().getResources(), typedValue.resourceId, getCurrentContextTheme());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return defaultColorStateList;
        }
    }

    /**
     * 根据给定的attrId获取ColorStateList对象
     * @param defaultColorStateList
     * @param attrId
     * */
    public ColorStateList getColorStateListByAttr(int attrId, ColorStateList defaultColorStateList) {
        return innerGetColorStateListByAttr(attrId, defaultColorStateList);
    }

    /**
     * 根据给定的attrId获取ColorStateList对象
     * @param attrId
     * */
    public ColorStateList getColorStateListByAttr(int attrId) {
        return innerGetColorStateListByAttr(attrId, null);
    }

    /**
     * # 根据给定的attrId获取float的值
     * @param attrId
     * */
    private float innerGetFloatByAttrId(int attrId, float defaultFloat) {
        if (!isCurrentContextValid()) return defaultFloat;
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null) return defaultFloat;
        try {
            defaultFloat = typedValue.getFloat();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return defaultFloat;
        }
    }

    /**
     * # 根据给定的attrId获取dimen的值
     * @param attrId
     * */
    private float innerDimensionByAttrId(int attrId, float defaultFloat) {
        if (!isCurrentContextValid()) return defaultFloat;
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null) return defaultFloat;
        try {
            defaultFloat = typedValue.getDimension(getContext().getResources().getDisplayMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return defaultFloat;
        }
    }

    /**
     * 根据给定的attrId获取float的值
     * @param defaultFloat
     * @param attrId
     * */
    public float getFloatByAttrId(int attrId, float defaultFloat) {
        return innerGetFloatByAttrId(attrId, defaultFloat);
    }

    /**
     * 根据给定的attrId获取float的值
     * @param attrId
     * */
    public float getFloatByAttrId(int attrId) {
        return innerGetFloatByAttrId(attrId, DEFAULT_FLOAT_VALUE);
    }

    /**
     * 根据给定的attrId获取dimen的值
     * @param defaultFloat
     * @param attrId
     * */
    public float getDimenByAttrId(int attrId, float defaultFloat) {
        return innerDimensionByAttrId(attrId, defaultFloat);
    }

    /**
     * 根据给定的attrId获取dimen的值
     * @param attrId
     * */
    public float getDimenByAttrId(int attrId) {
        return innerDimensionByAttrId(attrId, DEFAULT_FLOAT_VALUE);
    }

    /**
     * # 制作着色Drawable
     * @param drawable  需要进行着色的Drawable对象
     * @param colorStateList  需要着色的颜色值
     * @return Drawable
     * */
    private Drawable innerCreateTintDrawable(@NonNull Drawable drawable, ColorStateList colorStateList) {
        if (drawable == null) return drawable;  // 设置的drawable为Null.
        Drawable tintDrawable = DrawableCompat.wrap(drawable.mutate());
        if (tintDrawable == null) return drawable;
        DrawableCompat.setTintList(tintDrawable, colorStateList);
        if (tintDrawable == null) return drawable;
        return tintDrawable;
    }

    /**
     * 制作着色Drawable
     * @param drawable
     * @param colorStateList
     * */
    public Drawable createTintDrawable(@NonNull Drawable drawable, ColorStateList colorStateList) {
        return innerCreateTintDrawable(drawable, colorStateList);
    }

    /**
     * 设置View背景
     * @param view
     * @param attrId
     * */
    public void setBackgroundResourceByAttr(@NonNull View view, int attrId) {
        if (view == null) return ;
        int identifier = getIdentifierByAttrId(attrId);
        if (identifier == 0) return ;
        view.setBackgroundResource(identifier);
    }

    /**
     * 为ImageView设置attrId对应的资源作为图片资源
     * */
    public void setImageResourceByAttr(@NonNull ImageView imageView, int attrId) {
        if (imageView == null) return ;
        int identifier = getIdentifierByAttrId(attrId);
        if (identifier == 0) return ;
        imageView.setImageResource(identifier);
    }

    /**
     * 为View设置指定着色的Drawable作为Background
     * @param view
     * @param drawable
     * @param colorStateList
     * */
    public void setTintBackground(@NonNull View view, @NonNull Drawable drawable, ColorStateList colorStateList) {
        if (view == null || drawable == null) return ;
        Drawable tintedDrawable = createTintDrawable(drawable, colorStateList);
        if (tintedDrawable == null) return ;
        view.setBackground(tintedDrawable);
    }

    /**
     * 为View设置指定着色的Drawable作为Background
     * @param view
     * @param attrId
     * @param drawable
     * @param defaultColorStateList
     * */
    public void setTintBackgroundBtAttr(@NonNull View view, @NonNull Drawable drawable, int attrId, ColorStateList defaultColorStateList) {
        if (view == null || drawable == null) return ;
        ColorStateList tintColor = innerGetColorStateListByAttr(attrId, defaultColorStateList);
        Drawable tintedDrawable = innerCreateTintDrawable(drawable, tintColor);
        if (tintedDrawable == null) return ;
        view.setBackground(tintedDrawable);
    }

    /**
     * 为View设置指定着色的Drawable作为Background
     * @param view
     * @param attrId
     * @param drawable
     * */
    public void setTintBackgroundBtAttr(@NonNull View view, @NonNull Drawable drawable, int attrId) {
        if (view == null || drawable == null) return ;
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, null);
        Drawable tintedDrawable = innerCreateTintDrawable(drawable, colorStateList);
        if (tintedDrawable == null) return ;
        view.setBackground(tintedDrawable);
    }

    /**
     * 直接根据View当前Background设置着色Drawable并重新设置背景
     * @param view
     * @param colorStateList
     * */
    public void tintBackground(@NonNull View view, ColorStateList colorStateList) {
        if (view == null) return ;
        Drawable backgroundDrawable = view.getBackground();
        if (backgroundDrawable == null) return ;
        Drawable tintedDrawable = createTintDrawable(backgroundDrawable, colorStateList);
        if (tintedDrawable == null) return ;
        view.setBackground(tintedDrawable);
    }

    /**
     * 直接根据View当前Background设置着色Drawable并重新设置背景
     * @param view
     * @param attrId
     * @param defaultColorStateList
     * */
    public void tintBackgroundByAttr(@NonNull View view, int attrId, ColorStateList defaultColorStateList) {
        if (view == null) return ;
        Drawable drawable = view.getBackground();
        if (drawable == null) return ;
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, defaultColorStateList);
        Drawable tintedDrawable = innerCreateTintDrawable(drawable, colorStateList);
        if (tintedDrawable == null) return ;
        view.setBackground(tintedDrawable);
    }

    /**
     * 直接根据View当前Background设置着色Drawable并重新设置背景
     * @param view
     * @param attrId
     * */
    public void tintBackgroundByAttr(@NonNull View view, int attrId) {
        if (view == null) return ;
        Drawable drawable = view.getBackground();
        if (drawable == null) return ;
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, null);
        Drawable tintedDrawable = innerCreateTintDrawable(drawable, colorStateList);
        if (tintedDrawable == null) return ;
        view.setBackground(tintedDrawable);
    }

    /**
     * 为指定ImageView设置指定的着色Drawable
     * @param colorStateList
     * @param imageView
     * @param drawable
     * */
    public void setTintImageDrawable(@NonNull ImageView imageView, @NonNull Drawable drawable, ColorStateList colorStateList) {
        if (imageView == null || drawable == null) return ;
        Drawable tintedDrawable = createTintDrawable(drawable, colorStateList);
        if (tintedDrawable == null) return ;
        imageView.setImageDrawable(tintedDrawable);
    }

    /**
     * 为指定ImageView设置指定的着色Drawable
     * @param defaultColorStateList
     * @param imageView
     * @param drawable
     * @param attrId
     * */
    public void setTintImageDrawableByAttr(@NonNull ImageView imageView, @NonNull Drawable drawable, int attrId, ColorStateList defaultColorStateList) {
        if (imageView == null || drawable == null) return ;
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, defaultColorStateList);
        Drawable tintedDrawable = innerCreateTintDrawable(drawable, colorStateList);
        if (tintedDrawable == null) return ;
        imageView.setImageDrawable(tintedDrawable);
    }

    /**
     * 为指定ImageView设置指定的着色Drawable
     * @param imageView
     * @param drawable
     * @param attrId
     * */
    public void setTintImageDrawableByAttr(@NonNull ImageView imageView, @NonNull Drawable drawable, int attrId) {
        if (imageView == null || drawable == null) return ;
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, null);
        Drawable tintedDrawable = innerCreateTintDrawable(drawable, colorStateList);
        if (tintedDrawable == null) return ;
        imageView.setImageDrawable(tintedDrawable);
    }

    /**
     * 直接根据ImageView当前ImageDrawable设置着色Drawable并重新设置ImageDrawable
     * @param colorStateList
     * @param imageView
     * */
    public void tintImageDrawable(@NonNull ImageView imageView, ColorStateList colorStateList) {
        if (imageView == null) return ;
        Drawable originDrawable = imageView.getDrawable();
        if (originDrawable == null) return ;
        Drawable tintedDrawable = createTintDrawable(originDrawable, colorStateList);
        if (tintedDrawable == null) return ;
        imageView.setImageDrawable(tintedDrawable);
    }

    /**
     * 直接根据ImageView当前ImageDrawable设置着色Drawable并重新设置ImageDrawable
     * @param defaultColorStateList
     * @param imageView
     * @param attrId
     * */
    public void tintImageDrawableByAttr(@NonNull ImageView imageView, int attrId, ColorStateList defaultColorStateList) {
        if (imageView == null) return ;
        Drawable drawable = imageView.getDrawable();
        if (drawable == null) return ;
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, defaultColorStateList);
        Drawable tintedDrawable = innerCreateTintDrawable(drawable, colorStateList);
        if (tintedDrawable == null) return ;
        imageView.setImageDrawable(tintedDrawable);
    }

    /**
     * 直接根据ImageView当前ImageDrawable设置着色Drawable并重新设置ImageDrawable
     * @param imageView
     * @param attrId
     * */
    public void tintImageDrawableByAttr(@NonNull ImageView imageView, int attrId) {
        if (imageView == null) return ;
        Drawable drawable = imageView.getDrawable();
        if (drawable == null) return ;
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, null);
        Drawable tintedDrawable = innerCreateTintDrawable(drawable, colorStateList);
        if (tintedDrawable == null) return ;
        imageView.setImageDrawable(tintedDrawable);
    }

    /**
     * 设置View的Alpha透明度
     * @param view
     * @param attrId
     * @param defaultFloat
     * */
    public void setAlphaByAttr(@NonNull View view, int attrId, float defaultFloat) {
        if (view == null) return ;
        float alpha = innerGetFloatByAttrId(attrId, defaultFloat);
        view.setAlpha(alpha);
    }

    /**
     * 设置View的Alpha透明度
     * @param view
     * @param attrId
     * */
    public void setAlphaByAttr(@NonNull View view, int attrId) {
        if (view == null) return ;
        float alpha = innerGetFloatByAttrId(attrId, DEFAULT_FLOAT_VALUE);
        view.setAlpha(alpha);
    }

    /**
     * 设置背景颜色值
     * @param view
     * @param attrId
     * @param defaultColor
     * */
    public void setBackgroundColorByAttr(@NonNull View view, int attrId, int defaultColor) {
        if (view == null) return ;
        int backgroundColor = innerGetColorByAttr(attrId, defaultColor);
        view.setBackgroundColor(backgroundColor);
    }

    /**
     * 设置背景颜色值
     * @param view
     * @param attrId
     * */
    public void setBackgroundColorByAttr(@NonNull View view, int attrId) {
        if (view == null) return ;
        int backgroundColor = innerGetColorByAttr(attrId, DEFAULT_BACKGROUND_COLOR);
        view.setBackgroundColor(backgroundColor);
    }

    /**
     * 设置textView字体颜色值
     * @param defaultColorStateList
     * @param textView
     * @param attrId
     * */
    public void setTextColorByAttr(@NonNull TextView textView, int attrId, ColorStateList defaultColorStateList) {
        if (textView == null) return ;
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, defaultColorStateList);
        textView.setTextColor(colorStateList);
    }

    /**
     * 设置textView字体颜色值
     * @param textView
     * @param attrId
     * */
    public void setTextColorByAttr(@NonNull TextView textView, int attrId) {
        if (textView == null) return ;
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, null);
        textView.setTextColor(colorStateList);
    }

    /**
     * 设置textView提示颜色值
     * @param textView
     * @param attrId
     * */
    public void setHintTextColorByAttr(@NonNull TextView textView, int attrId) {
        if (textView == null) return ;
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, null);
        textView.setHintTextColor(colorStateList);
    }

    /**
     * 设置textView提示颜色值
     * @param defaultColorStateList
     * @param textView
     * @param attrId
     * */
    public void setHintTextColorByAttr(@NonNull TextView textView, int attrId, ColorStateList defaultColorStateList) {
        if (textView == null) return ;
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, defaultColorStateList);
        textView.setHintTextColor(colorStateList);
    }
}