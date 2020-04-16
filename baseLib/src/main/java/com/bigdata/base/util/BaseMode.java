package com.bigdata.base.util;

import android.view.View;

import java.util.Map;

/**
 * Created by 通子 on 2017/12/18.
 */

public interface BaseMode {
    public void initView();

    public void initClick();

    public void preInitData();

    public void initData();

    public void showToast(String content);

    public String getDecodeParams(Map<String, Object> params, String token);

    public String getDecodeParams(Map<String, Object> params);

    public void retrofitRequest(int requestCode, Object requestCall, Map<String, Object> params);

    public void retrofitRequestWithoutLoading(int requestCode, Object requestCall, Map<String, Object> params);

    public void retrofitRequest(final int requestCode, Object requestCall, Map<String, Object> params, final boolean isshowLoading);

    public abstract void onSuccess(int requestCode,int returnCode, String result);

    public abstract void onParseError(int requestCode);

    public abstract void onFail(int requestCode, Throwable t);

    public abstract void onErrorCode(int requestCode, int errorCode, String errorMsg);

    public abstract void onFinish(int requestCode, boolean isHideLoading);

    public void showKeyboard(View view);

    public void hideKeyboard(View view);

    public void showLoading();

    public void hideLoading();

}
