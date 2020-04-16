package com.bigdata.base.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigdata.base.R;

/**
 * @auther Leo--李智
 * Create at 2018/1/12 16:44
 * TitleView
 */
public class TitleLibView extends LinearLayout {
    private Context mContext;
    private TextView title, rightTv;
    private ImageView back;

    public TitleLibView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TitleLibView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(final Context mContext, AttributeSet attrs) {
        this.mContext = mContext;
        LayoutInflater.from(mContext).inflate(R.layout.view_title, this);
        title = (TextView) findViewById(R.id.title_content);
        rightTv = (TextView) findViewById(R.id.title_righttv);
        rightTv.setVisibility(View.VISIBLE);
        back = (ImageView) findViewById(R.id.title_back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });

    }

    /**
     * 设置TITLE
     * @param tv
     */
    public void setTitleTv(String tv) {
        title.setText(tv);
    }

    public void setOnRightTvClickListener(OnClickListener rightListener) {
        rightTv.setOnClickListener(rightListener);
    }

    /**
     * 设置右边TextView文字
     *
     * @param tv
     */
    public void setRightTv(String tv) {
        rightTv.setText(tv);
    }
}
