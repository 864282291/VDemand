package com.bigdata.base.act;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bigdata.base.request.RequestCallback;
import com.bigdata.base.request.RequestCode;
import com.bigdata.base.swipe.act.SwipeBackActivity;
import com.bigdata.base.util.BaseMode;
import com.bigdata.base.util.NetConfig;
import com.bigdata.base.util.ToastUtil;
import com.bigdata.base.util.entryption.DecodeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 通子 on 2017/12/12.
 */

public abstract class BaseActivity extends SwipeBackActivity implements BaseMode {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        swipeEnable = getSwipEnable();
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;
//        setStatusBar();
        setContentView(getLayout());
        initView();
        initClick();
        preInitData();
        initData();
    }

//    protected void setStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.transport));
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
//    }

    public void showToast(String content) {
        ToastUtil.showMessage(mContext, content);
    }

    @Override
    public String getDecodeParams(Map<String, Object> params, String token) {
        Map<String, Object> param;
        if (token == null)
            param = DecodeUtil.decodeParams(params, mContext);
        else
            param = DecodeUtil.decodeParams(params, mContext, token);
        return (String) (param.get("request"));
    }

    @Override
    public String getDecodeParams(Map<String, Object> params) {
        return getDecodeParams(params, null);
    }

    @Override
    public void retrofitRequest(int requestCode, Object requestCall, Map<String, Object> params) {
        retrofitRequest(requestCode, requestCall, params, true);
    }

    @Override
    public void retrofitRequestWithoutLoading(int requestCode, Object requestCall, Map<String, Object> params) {
        retrofitRequest(requestCode, requestCall, params, false);
    }

    @Override
    public void retrofitRequest(final int requestCode, Object requestCall, Map<String, Object> params, final boolean isshowLoading) {
        if (isshowLoading)
            showLoading();
        String requestParams = getDecodeParams(params);
        try {
            Method[] methods = requestCall.getClass().getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (method.getName().equals("call")) {
                    method.setAccessible(true);
                    Call<RequestBody> commonCall = (Call<RequestBody>) method.invoke(requestCall, requestParams);
                    if (commonCall != null)
                        commonCall.enqueue(new RequestCallback() {
                            @Override
                            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                                onFinish(requestCode, isshowLoading);
                                RequestBody commonBean = response.body();
                                String result = null;
                                result = commonBean.toString();
                                if (NetConfig.ISMD5DECODE) {
                                    result = DecodeUtil.getDecodeJson(result);
                                }
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(result);
                                    int returnCode = jsonObject.getInt("code");
                                    String returnMsg = "请求成功";
                                    if (returnCode != 1) {
                                        returnMsg = "";
                                    }
                                    String returnData = jsonObject.getString("data");
                                    if (returnCode == RequestCode.SUCCESS_CODE) {
                                        //成功获取返回值
                                        onSuccess(requestCode, returnCode, result);
                                    } else {
                                        onErrorCode(requestCode, returnCode, returnMsg);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    onParseError(requestCode);
                                }
                            }

                            @Override
                            public void onFailure(Call<RequestBody> call, Throwable t) {
                                onFinish(requestCode, isshowLoading);
                                onFail(requestCode, t);
                            }
                        });
                    break;
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onParseError(int requestCode) {
//        showToast("数据解析错误");
    }

    @Override
    public void onFail(int requestCode, Throwable t) {
//        showToast("请求失败");
    }

    @Override
    public void onErrorCode(int requestCode, int errorCode, String errorMsg) {
        //token过期逻辑处理
//        showToast(errorMsg);
    }

    @Override
    public void onFinish(int requestCode, boolean isHideLoading) {
        if (isHideLoading)
            hideLoading();
    }

    @Override
    public void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.showSoftInput(view, 0);
    }

    @Override
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public abstract int getLayout();

    public boolean getSwipEnable() {
        return true;
    }
}
