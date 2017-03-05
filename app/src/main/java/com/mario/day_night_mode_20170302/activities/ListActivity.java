package com.mario.day_night_mode_20170302.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mario.day_night_mode_20170302.MyApplication;
import com.mario.day_night_mode_20170302.R;
import com.mario.day_night_mode_20170302.helpers.MarioResourceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mario on 2017-03-05.
 */

public class ListActivity extends BaseActivity {

    @BindView(R.id.custom_id_app_background)
    public RelativeLayout mAppBackground;
    @BindView(R.id.custom_id_title_layout)
    public View mTitleLayout;
    @BindView(R.id.custom_id_title_status_bar)
    public View mStatusBar;

    @BindView(R.id.list_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.list_checkbox_thumb)
    View mCheckboxThumb;
    @BindView(R.id.list_checkbox_bg)
    RelativeLayout mCheckboxBg;
    @BindView(R.id.list_main_title)
    TextView mMainTitle;

    private CustomAdapter mAdapter;
    private int thumb_margin_left_day = 0;
    private int thumb_margin_left_night = 0;

    private boolean tag_is_animation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAllViews();
        initAllDatum();
    }

    @Override
    public void initAllViews() {
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
    }

    @Override
    public void initAllDatum() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CustomAdapter();
        mRecyclerView.setAdapter(mAdapter);

        thumb_margin_left_day = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        thumb_margin_left_night = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, getResources().getDisplayMetrics());
    }

    @Override
    public void notifyByThemeChanged() {
        MarioResourceHelper helper = MarioResourceHelper.getInstance(getContext());

        helper.setBackgroundResourceByAttr(mAppBackground, R.attr.custom_attr_app_bg);
        helper.setBackgroundResourceByAttr(mStatusBar, R.attr.custom_attr_app_title_layout_bg);
        helper.setBackgroundResourceByAttr(mTitleLayout, R.attr.custom_attr_app_title_layout_bg);

        helper.setTextColorByAttr(mMainTitle, R.attr.custom_attr_main_title_color);
        helper.setBackgroundResourceByAttr(mCheckboxBg, R.attr.custom_attr_check_box_bg);
        helper.setBackgroundResourceByAttr(mCheckboxThumb, R.attr.custom_attr_check_box_thumb_bg);

        if (mAdapter != null) mAdapter.notifyByThemeChanged();
    }

    @OnClick(R.id.list_checkbox_bg)
    public void OnCheckboxClickListener(View view) {
        doCheckboxAnimation();
    }

    private void doCheckboxAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(240);
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (tag_is_animation) {
                    Intent intent = new Intent(getContext(), AnimatorActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    ((MyApplication) getApplication()).notifyByThemeChanged(); // 发送主题变更通知，让每一个注册监听事件的Activity主动更新UI.
                }
            }
        });

        animatorSet.play(obtainCheckboxAnimator());

        animatorSet.start();
    }

    private Animator obtainCheckboxAnimator() {
        int start = getThemeTag() == -1 ? thumb_margin_left_night : thumb_margin_left_day;
        int end = getThemeTag() == -1 ? thumb_margin_left_day : thumb_margin_left_night;
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                RelativeLayout.MarginLayoutParams layoutParams = (RelativeLayout.MarginLayoutParams) mCheckboxThumb.getLayoutParams();
                layoutParams.leftMargin = value;
                mCheckboxThumb.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        public TextView item_title;
        @BindView(R.id.item_description)
        public TextView item_description;
        @BindView(R.id.item_div_top)
        public View item_div_top;
        @BindView(R.id.item_div_bottom)
        public View item_div_bottom;
        @BindView(R.id.item_arrow)
        public ImageView item_arrow;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

        public CustomAdapter() {
            initAllThemeAttrs();
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CustomViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.include_layout_item, null));
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            holder.item_div_top.setVisibility(position == (0) ? View.VISIBLE : View.GONE);
            ViewGroup.LayoutParams originLayoutParams = holder.item_div_bottom.getLayoutParams();
            if (originLayoutParams != null) {
                LinearLayout.MarginLayoutParams marginLayoutParams = (LinearLayout.MarginLayoutParams) originLayoutParams;
                marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, position == getItemCount() - 1 ? 0 : 12, getContext().getResources().getDisplayMetrics());
                holder.item_div_bottom.setLayoutParams(marginLayoutParams);
            }

            holder.itemView.setBackgroundColor(item_background_color);
            holder.item_description.setTextColor(item_sub_text_color);
            holder.item_div_bottom.setBackgroundColor(item_div_color);
            holder.item_div_top.setBackgroundColor(item_div_color);
            holder.item_title.setTextColor(item_main_text_color);

            tintDrawable(holder.item_arrow, item_arrow_tint_color);
        }

        @Override
        public int getItemCount() {
            return 36;
        }

        private int item_background_color = Color.parseColor("#00000000");
        private int item_arrow_tint_color = Color.parseColor("#00000000");
        private int item_main_text_color = Color.parseColor("#00000000");
        private int item_sub_text_color = Color.parseColor("#00000000");
        private int item_div_color = Color.parseColor("#00000000");

        private void initAllThemeAttrs() {
            MarioResourceHelper helper = MarioResourceHelper.getInstance(getContext());
            item_background_color = helper.getColorByAttr(R.attr.custom_attr_app_content_layout_bg);
            item_arrow_tint_color = helper.getColorByAttr(R.attr.custom_attr_sub_text_color);
            item_main_text_color = helper.getColorByAttr(R.attr.custom_attr_main_text_color);
            item_sub_text_color = helper.getColorByAttr(R.attr.custom_attr_sub_text_color);
            item_div_color = helper.getColorByAttr(R.attr.custom_attr_app_content_layout_div_color);
        }

        public void notifyByThemeChanged() {
            initAllThemeAttrs();
            notifyDataSetChanged();
        }

        private void tintDrawable(ImageView imageView, int tintColor) {
            if (imageView == null) return ;
            Drawable originDrawable = imageView.getDrawable();
            if (originDrawable == null) return ;
            Drawable tintDrawable = DrawableCompat.wrap(originDrawable.mutate());
            if (tintDrawable == null) return ;
            DrawableCompat.setTint(tintDrawable, tintColor);
            if (tintDrawable == null) return ;
            imageView.setImageDrawable(tintDrawable);
        }
    }
}
