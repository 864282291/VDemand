package com.bigdata.base.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 通子 on 2017/12/12.
 */

public class ToastUtil {
    public static void showMessage(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
