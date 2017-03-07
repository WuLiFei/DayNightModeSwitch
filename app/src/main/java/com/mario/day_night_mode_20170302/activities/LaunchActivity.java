package com.mario.day_night_mode_20170302.activities;

import android.content.Intent;
import android.os.Message;
import android.os.Handler;
import android.os.Bundle;

import com.mario.day_night_mode_20170302.R;
import com.mario.day_night_mode_20170302.common.KeyStore;

/**
 * Created by Mario on 2017-03-02.
 */

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initAllViews();
        initAllDatum();
    }

    @Override
    public void initAllViews() {
        //TODO
    }

    @Override
    public void initAllDatum() {
        new InternalHandler().sendEmptyMessageDelayed(KeyStore.KEY_SKIP_LUNCH_TO_MAIN, 800);
    }

    @Override
    public void notifyByThemeChanged() {
        // TODO
    }

    private class InternalHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) return ;
            switch (msg.what) {
                case KeyStore.KEY_SKIP_LUNCH_TO_MAIN:
                    Intent intent = new Intent(getContext(), ThemeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}
