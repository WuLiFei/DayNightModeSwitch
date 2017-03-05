package com.mario.day_night_mode_20170302;

import android.app.Application;

import com.mario.day_night_mode_20170302.interfaces.ThemeChangeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mario on 2017-03-05.
 */

public class MyApplication extends Application {

    private List<ThemeChangeObserver> mThemeChangeObserverStack; //  主题切换监听栈

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 获得observer堆栈
     * */
    private List<ThemeChangeObserver> obtainThemeChangeObserverStack() {
        if (mThemeChangeObserverStack == null) mThemeChangeObserverStack = new ArrayList<>();
        return mThemeChangeObserverStack;
    }

    /**
     * 向堆栈中添加observer
     * */
    public void registerObserver(ThemeChangeObserver observer) {
        if (observer == null || obtainThemeChangeObserverStack().contains(observer)) return ;
        obtainThemeChangeObserverStack().add(observer);
    }

    /**
     * 从堆栈中移除observer
     * */
    public void unregisterObserver(ThemeChangeObserver observer) {
        if (observer == null || !(obtainThemeChangeObserverStack().contains(observer))) return ;
        obtainThemeChangeObserverStack().remove(observer);
    }

    /**
     * 向堆栈中所有对象发送更新UI的指令
     * */
    public void notifyByThemeChanged() {
        List<ThemeChangeObserver> observers = obtainThemeChangeObserverStack();
        for (ThemeChangeObserver observer : observers) {
            observer.loadingCurrentTheme(); //
            observer.notifyByThemeChanged(); //
        }
    }
}
