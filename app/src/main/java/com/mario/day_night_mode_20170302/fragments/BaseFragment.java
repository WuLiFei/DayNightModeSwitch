package com.mario.day_night_mode_20170302.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mario.day_night_mode_20170302.MyApplication;
import com.mario.day_night_mode_20170302.interfaces.ThemeChangeObserver;

/**
 * Created by Mario on 2017-03-06.
 */

public abstract class BaseFragment extends Fragment implements ThemeChangeObserver {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) ((Activity) getContext()).getApplication()).registerObserver(this);
    }

    @Override
    public void onDestroy() {
        ((MyApplication) ((Activity) getContext()).getApplication()).unregisterObserver(this);
        super.onDestroy();
    }

    @Override
    public void loadingCurrentTheme() {

    }
}
