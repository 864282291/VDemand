
package com.bigdata.base.swipe.act;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;

import com.bigdata.base.swipe.helper.SwipeBackActivityHelper;
import com.bigdata.base.swipe.inter.SwipeBackActivityBase;
import com.bigdata.base.swipe.utils.Utils;
import com.bigdata.base.swipe.view.SwipeBackLayout;

public class SwipeBackActivity extends FragmentActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;
    public boolean swipeEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (swipeEnable) {
            mHelper = new SwipeBackActivityHelper(this);
            mHelper.onActivityCreate();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mHelper != null)
            mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        if (mHelper != null)
            return mHelper.getSwipeBackLayout();
        return null;
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
