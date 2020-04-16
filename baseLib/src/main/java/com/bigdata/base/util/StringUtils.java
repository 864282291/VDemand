package com.bigdata.base.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Leo
 * @version V1.0
 * @Title: DataConverUtils.java
 * @Package com.bigdata.doctor.utils.data
 * @Description: TODO 数据转换以及正则
 * @date 2016-11-14 上午11:38:18
 */
public class StringUtils {
    /**
     * 判断手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        String check = "[1][34578]\\d{9}";
        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(mobile.trim());
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 判断邮编 postcode
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]d{5}(?!d)";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 判断用户名是否包含特殊符号
     *
     * @param name
     * @return
     */
    public static boolean isName(String name) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(name);
        return m.find();
    }

    /**
     * Base64对Bitmap进行加码；
     *
     * @param bit
     * @return
     */
    public static String BitmapToStringByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(CompressFormat.JPEG, 100, bos);// 参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * Base64对数据流进行加码；
     *
     * @param uri
     * @return
     */
    public static String UriToStringByBase64(String uri) {
        FileInputStream fin;
        try {
            fin = new FileInputStream(uri);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            fin.close();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String Base64ToString(String base64String) {
        // 对base64加密后的数据进行解密
        String string = new String(Base64.decode(base64String.getBytes(),
                Base64.DEFAULT));
        return string;
    }

    public static Bitmap Base64ToBitmap(String base64String) {
        // 对base64加密后的数据进行解密
        // String string = new String(Base64.decode(base64String.getBytes(),
        // Base64.DEFAULT));
        byte[] decode = Base64.decode(base64String.getBytes(), Base64.DEFAULT);
        if (decode.length != 0) {
            return BitmapFactory.decodeByteArray(decode, 0, decode.length);
        } else {
            return null;
        }
    }

    /**
     * 密码长度判断
     */
    public static boolean isPassowrd(String password) {
        if (password.length() >= 6 && password.length() <= 15) {
            return true;
        }
        return false;
    }

    /**
     * 获取随机数
     */
//    public static String getTimeRandom() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(1000000))
//                .getBytes());
//    }

    private static String numStrTmp = "";
    private static String numStr = "";
    private static int[] numArray = new int[4];

    /**
     * 验证码生成器
     */
    public static String initNum(String mobile) {
        numStr = "";
        numStrTmp = "";
        for (int i = 0; i < numArray.length; i++) {
            int numIntTmp = new Random().nextInt(10);
            numStrTmp = String.valueOf(numIntTmp);
            numStr = numStr + numStrTmp;
            numArray[i] = numIntTmp;
        }
        try {
            numStr = URLEncoder.encode(numStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return numStr;
    }

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s);
    }


    /**
     * 个体位数加零
     */
    public static String addZero(int a) {
        if (a < 10) {
            return "0" + a;
        }
        return "" + a;
    }

    /**
     * 是否符合密码格式
     * @param userPhone
     * @return
     */
    public static boolean isUserPass(String userPhone){
        if(TextUtils.isEmpty(userPhone)||userPhone.length()<6||userPhone.length()>16){
            return false;
        }
        return true;
    }
}
