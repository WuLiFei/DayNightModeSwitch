package com.mario.day_night_mode_20170302.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mario.day_night_mode_20170302.MyApplication;
import com.mario.day_night_mode_20170302.R;
import com.mario.day_night_mode_20170302.common.GlideCircleTransform;
import com.mario.day_night_mode_20170302.fragments.MainFragment;
import com.mario.day_night_mode_20170302.helpers.MarioResourceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mario on 2017-03-03.
 */

public class ThemeActivity extends BaseActivity {

    @BindView(R.id.theme_btn_turn_night)
    public TextView mBtnTurnNight;
    @BindView(R.id.theme_btn_turn_day)
    public TextView mBtnTurnDay;
    @BindView(R.id.custom_id_app_background)
    public LinearLayout mAppBackground;
    @BindView(R.id.custom_id_title_layout)
    public View mTitleLayout;
    @BindView(R.id.custom_id_title_status_bar)
    public View mStatusBar;
    @BindView(R.id.theme_view_pager)
    public ViewPager mViewPager;
    @BindView(R.id.theme_user_photo)
    public ImageView mUserPhoto;
    @BindView(R.id.theme_nickname)
    public TextView mNickname;
    @BindView(R.id.theme_remark)
    public TextView mRemark;

    private boolean tag_is_animation = false;
    private Drawable mUserPhotoPlaceHolder = null;

    private CustomViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAllViews();
        initAllDatum();
    }

    @Override
    public void initAllViews() {
        setContentView(R.layout.activity_theme);
        ButterKnife.bind(ThemeActivity.this);
    }

    @Override
    public void initAllDatum() {
        mAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        initBtnStatus();
        mUserPhotoPlaceHolder = MarioResourceHelper.getInstance(getContext()).getDrawableByAttr(R.attr.custom_attr_user_photo_place_holder);
        Glide.with(getContext()).load(getResources().getString(R.string.user_photo_url)).placeholder(mUserPhotoPlaceHolder).transform(new GlideCircleTransform(getContext())).into(mUserPhoto);
    }

    private void initBtnStatus() {
        int tag = getThemeTag();
        mBtnTurnDay.setEnabled(tag == 1 ? false : true);
        mBtnTurnNight.setEnabled(tag == 1 ? true : false);
    }

    @OnClick({R.id.theme_btn_turn_day, R.id.theme_btn_turn_night})
    public void Clicked(View view) {
        if (tag_is_animation) {
            Intent intent = new Intent(getContext(), AnimatorActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            switchCurrentThemeTag();
            ((MyApplication) getApplication()).notifyByThemeChanged();
//            recreate();
        }
    }

    @Override
    public void notifyByThemeChanged() {
        MarioResourceHelper helper = MarioResourceHelper.getInstance(getContext());
        helper.setBackgroundResourceByAttr(mAppBackground, R.attr.custom_attr_app_bg);
        helper.setBackgroundResourceByAttr(mStatusBar, R.attr.custom_attr_app_title_layout_bg);
        helper.setBackgroundResourceByAttr(mTitleLayout, R.attr.custom_attr_app_title_layout_bg);

        helper.setBackgroundResourceByAttr(mBtnTurnDay, R.attr.custom_attr_btn_bg);
        helper.setTextColorByAttr(mBtnTurnDay, R.attr.custom_attr_btn_text_color);
        helper.setBackgroundResourceByAttr(mBtnTurnNight, R.attr.custom_attr_btn_bg);
        helper.setTextColorByAttr(mBtnTurnNight, R.attr.custom_attr_btn_text_color);

        helper.setAlphaByAttr(mUserPhoto, R.attr.custom_attr_user_photo_alpha);

        helper.setTextColorByAttr(mNickname, R.attr.custom_attr_nickname_text_color);
        helper.setTextColorByAttr(mRemark, R.attr.custom_attr_remark_text_color);

        mUserPhotoPlaceHolder = helper.getDrawableByAttr(R.attr.custom_attr_user_photo_place_holder);

        initBtnStatus(); //
    }

    private class CustomViewPagerAdapter extends FragmentPagerAdapter {

        public CustomViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return new MainFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
