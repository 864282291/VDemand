package com.bigdata.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bigdata.base.act.BaseActivity;
import com.bigdata.base.util.BaseMode;

import java.util.Map;

/**
 * @auther Leo--李智
 * Create at 2017/10/16 15:49
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment implements BaseMode {

    //是否开启View反馈
    private boolean isEmpty = false;
    public Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getFragView() != 0) {
            return inflater.inflate(getFragView(), null);
        }
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        win.setAttributes(winParams);
//        mSystemBar = new SystemBarTintManager(getActivity());
//        mSystemBar.setStatusBarTintEnabled(true);
//        mSystemBar.setNavigationBarTintEnabled(true);
        mContext = getActivity();
        initView();
        initClick();
        initData();
    }

    /**
     * 获取布局
     */
    public abstract int getFragView();


    /**
     * Toast提示
     */
    @Override
    public void showToast(String content) {
        ((BaseActivity) getActivity()).showToast(content);
    }

    @Override
    public String getDecodeParams(Map<String, Object> params, String token) {
        return ((BaseActivity) getActivity()).getDecodeParams(params, token);
    }

    @Override
    public String getDecodeParams(Map<String, Object> params) {
        return ((BaseActivity) getActivity()).getDecodeParams(params);
    }

    @Override
    public void retrofitRequest(int requestCode, Object requestCall, Map<String, Object> params) {
        ((BaseActivity) getActivity()).retrofitRequest(requestCode, requestCall, params);
    }

    @Override
    public void retrofitRequestWithoutLoading(int requestCode, Object requestCall, Map<String, Object> params) {
        ((BaseActivity) getActivity()).retrofitRequestWithoutLoading(requestCode, requestCall, params);
    }

    @Override
    public void retrofitRequest(int requestCode, Object requestCall, Map<String, Object> params, boolean isshowLoading) {
        ((BaseActivity) getActivity()).retrofitRequest(requestCode, requestCall, params, isshowLoading);
    }


    @Override
    public void onParseError(int requestCode) {
        ((BaseActivity) getActivity()).onParseError(requestCode);
    }

    @Override
    public void onFail(int requestCode, Throwable t) {
        ((BaseActivity) getActivity()).onFail(requestCode, t);
    }

    @Override
    public void onErrorCode(int requestCode, int errorCode, String errorMsg) {
        ((BaseActivity) getActivity()).onErrorCode(requestCode, errorCode, errorMsg);
    }

    @Override
    public void onFinish(int requestCode, boolean isHideLoading) {
        ((BaseActivity) getActivity()).onFinish(requestCode, isHideLoading);
    }

    /**
     * 显示键盘
     *
     * @param view
     */
    @Override
    public void showKeyboard(View view) {
        ((BaseActivity) getActivity()).showKeyboard(view);
    }

    /**
     * 隐藏键盘
     *
     * @param view
     */
    @Override
    public void hideKeyboard(View view) {
        ((BaseActivity) getActivity()).hideKeyboard(view);
    }

    @Override
    public void showLoading() {
        ((BaseActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((BaseActivity) getActivity()).hideLoading();
    }
}
