package com.bigdata.base.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 通子 on 2017/12/12.
 */

public class SpUtil {
    private SharedPreferences sp;
    private static SpUtil instance;
    public static Context mContext;

    public static SpUtil getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new SpUtil();
        }
        return instance;
    }

    private SpUtil() {
        sp = mContext.getSharedPreferences("com.bigdata", 0);
    }


    /**
     * 存储微信支付回调信息
     *
     * @param context
     */
    public static void PutWXPay(Context context, int status) {
        SharedPreferences sp = context.getSharedPreferences("WXPAY", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("WX_STATUS", status);
        editor.commit();
    }

    /**
     * 获取微信支付回调信息
     *
     * @param context
     * @return
     */
    public static int GetWXPay(Context context) {
        SharedPreferences sp = context.getSharedPreferences("WXPAY", 0);
        if (sp.contains("WX_STATUS")) {
            return sp.getInt("WX_STATUS", 11);
        }
        return 11;
    }

}
