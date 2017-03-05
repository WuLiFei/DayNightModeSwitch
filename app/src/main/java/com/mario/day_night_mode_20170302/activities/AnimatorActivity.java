package com.mario.day_night_mode_20170302.activities;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mario.day_night_mode_20170302.MyApplication;
import com.mario.day_night_mode_20170302.R;
import com.mario.day_night_mode_20170302.common.KeyStore;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mario on 2017-03-05.
 */

public class AnimatorActivity extends BaseActivity {

    @BindView(R.id.mode_change_animator_view)
    public ImageView mAnimatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        initAllViews();
        initAllDatum();
    }

    @Override
    public void initAllViews() {
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);
    }

    @Override
    public void initAllDatum() {
        mAnimatorView.setImageResource(getThemeTag() == -1 ? R.drawable.custom_drawable_mode_translation_turn_day_v16 : R.drawable.custom_drawable_mode_translation_turn_night_v16);
        sendEmptyMessageDelayed(KeyStore.KEY_TAG_ANIMATOR_START, 300);
    }

    @Override
    public void notifyByThemeChanged() {
        // TODO
    }

    private InternalHandler mHandler;
    private class InternalHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) return ;
            switch (msg.what) {
                case KeyStore.KEY_SKIP_ANIMATOR_FINISH:
                    finish();
                    break;
                case KeyStore.KEY_TAG_ANIMATOR_START:
                    startAnimator();
                    break;
                case KeyStore.KEY_TAG_ANIMATOR_STOP:
                    stopAnimator();
                    break;
                default:
                    break;
            }
        }

        /**
         *
         * */
        private void startAnimator() {
            Drawable drawable = mAnimatorView.getDrawable();
            if (drawable == null || !(drawable instanceof AnimationDrawable)) return ;
            ((AnimationDrawable) drawable).start();
            sendEmptyMessageDelayed(KeyStore.KEY_TAG_ANIMATOR_STOP, 1760);
        }

        /**
         *
         * */
        private void stopAnimator() {
            sendEmptyMessageDelayed(KeyStore.KEY_SKIP_ANIMATOR_FINISH, 360);
            switchCurrentThemeTag(); //
            ((MyApplication) getApplication()).notifyByThemeChanged(); //
        }
    }

    /**
     * */
    private void sendEmptyMessageDelayed(int what, long delay) {
        if (mHandler == null) mHandler = new InternalHandler();
        mHandler.sendEmptyMessageDelayed(what, delay);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
