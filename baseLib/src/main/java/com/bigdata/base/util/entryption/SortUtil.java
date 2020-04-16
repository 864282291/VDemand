package com.bigdata.base.util.entryption;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by 通子 on 2017/9/5.
 * 排序并拼接json字符串
 */

public class SortUtil {

    public static String[] getParams(Map<String, Object> map) {
        String s[] = new String[2];
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        StringBuffer sb = new StringBuffer();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (!key.equals("file")) {
                Object value = map.get(key);
                sb.append(key);
                sb.append("=");
                sb.append(value);
                if (i != (keys.size() - 1))
                    sb.append("&");
                try {
                    jsonObject.put(key, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        s[0] = sb.toString();
        s[1] = jsonObject.toString();
        return s;

    }
}
