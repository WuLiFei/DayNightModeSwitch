package com.mario.day_night_mode_20170302.fragments;

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
import android.widget.TextView;

import com.mario.day_night_mode_20170302.R;
import com.mario.day_night_mode_20170302.activities.RecyclerActivity;
import com.mario.day_night_mode_20170302.helpers.MarioResourceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mario on 2017-03-06.
 */

public class MainFragment extends BaseFragment {

    @BindView(R.id.fragment_main_recycler_view)
    public RecyclerView mRecyclerView;

    private CustomAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_main, null);
        initAllViews(contentView);
        initAllDatum();
        return contentView;
    }

    private void initAllViews(View contentView) {
        ButterKnife.bind(MainFragment.this, contentView);
    }

    private void initAllDatum() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CustomAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void notifyByThemeChanged() {
        if (mAdapter != null) mAdapter.notifyByThemeChanged(); //
    }

    /**
     * */
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

    /**
     * */
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

            holder.itemView.setOnClickListener(new CustomItemClickListener());
        }

        @Override
        public int getItemCount() {
            return 20;
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

        private class CustomItemClickListener implements View.OnClickListener {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RecyclerActivity.class);
                startActivity(intent);
            }
        }
    }
}
