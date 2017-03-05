package com.mario.day_night_mode_20170302.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mario.day_night_mode_20170302.MyApplication;
import com.mario.day_night_mode_20170302.R;
import com.mario.day_night_mode_20170302.interfaces.ThemeChangeObserver;

/**
 * Created by Mario on 2017-03-02.
 */

public abstract class BaseActivity extends AppCompatActivity implements ThemeChangeObserver {

    private final String KEY_MARIO_CACHE_THEME_TAG = "MarioCache_themeTag";

    public abstract void initAllViews();
    public abstract void initAllDatum();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityBeforeCreate();
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(attributes);
        }
    }

    private void setupActivityBeforeCreate() {
        ((MyApplication) getApplication()).registerObserver(this);
        loadingCurrentTheme();
    }

    @Override
    public void loadingCurrentTheme() {
        switch (getThemeTag()) {
            case  1:
                setTheme(R.style.MarioTheme_Day);
                break;
            case -1:
                setTheme(R.style.MarioTheme_Night);
                break;
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        View status = findViewById(R.id.custom_id_title_status_bar);
        if (status != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            status.getLayoutParams().height = getStatusBarHeight();
        }
    }

    /**
     * */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * */
    protected int getThemeTag() {
        SharedPreferences preferences = getSharedPreferences("MarioCache", Context.MODE_PRIVATE);
        return preferences.getInt(KEY_MARIO_CACHE_THEME_TAG, 1);
    }

    /**
     * */
    protected void setThemeTag(int tag) {
        SharedPreferences preferences = getSharedPreferences("MarioCache", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(KEY_MARIO_CACHE_THEME_TAG, tag);
        edit.commit();
    }

    public void switchCurrentThemeTag() {
        setThemeTag(0 - getThemeTag());
        loadingCurrentTheme();
    }

    /**
     * */
    public Context getContext() {
        return BaseActivity.this;
    }

    @Override
    protected void onDestroy() {
        ((MyApplication) getApplication()).unregisterObserver(this);
        super.onDestroy();
    }
}
