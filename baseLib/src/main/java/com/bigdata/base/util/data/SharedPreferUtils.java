package com.bigdata.base.util.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferUtils {

	public static void putJpushStatus(Context context, Boolean status) {
		SharedPreferences sp = context.getSharedPreferences("IS_JPUSH", 0);
		Editor editor = sp.edit();
		editor.putBoolean("status", status);
		editor.commit();
	}

	public static boolean getJpushStatus(Context context) {
		SharedPreferences sp = context.getSharedPreferences("IS_JPUSH", 0);
		if (sp.contains("status")) {
			return sp.getBoolean("status", false);
		}
		return false;
	}

	/**
	 * 存储美颜回调信息
	 * 
	 * @param context
	 */
	public static void PutSharedBeauty(Context context, int status) {
		SharedPreferences sp = context.getSharedPreferences("Beauty", 0);
		Editor editor = sp.edit();
		editor.putInt("Beauty", status);
		editor.commit();
	}

	/**
	 * 获取美颜回调信息
	 * 
	 * @param context
	 * @return
	 */
	public static int GetSharedBeauty(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Beauty", 0);
		if (sp.contains("Beauty")) {
			return sp.getInt("Beauty", 11);
		}
		return 11;
	}

	/**
	 * 存储微信支付回调信息
	 * 
	 * @param context
	 */
	public static void PutWXPay(Context context, int status) {
		SharedPreferences sp = context.getSharedPreferences("WXPAY", 0);
		Editor editor = sp.edit();
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

	/**
	 * 应用登录保存信息
	 */
	public static void SaveLoginUser(Context context, String user_name,
		String user_pwd, String icon) {
		SharedPreferences sp = context.getSharedPreferences("login", 0);
		Editor editor = sp.edit();
		editor.putString("user_name", user_name);
		editor.putString("user_pwd", user_pwd);
		editor.putString("user_icon", icon);
		editor.commit();
	}

	/**
	 * 应用登录信息保存
	 */
	public static String GetLoginUser(Context context, int user_flag) {
		SharedPreferences sp = context.getSharedPreferences("login", 0);
		if (user_flag == 1) {
			return sp.getString("user_name", null);
		}
		if (user_flag == 2) {
			return sp.getString("user_pwd", null);
		}
		if (user_flag == 3) {
			return sp.getString("user_icon", null);
		}
		return null;
	}

	/**
	 * 应用首次登录存储标志
	 */
	public static void SharedPutLogin(Context context, boolean is_show) {
		SharedPreferences sp = context.getSharedPreferences("Splash", 0);
		Editor editor = sp.edit();
		editor.putBoolean("SP_STATUS", is_show);
		editor.commit();
	}

	/**
	 * 应用首次登录读取标志
	 */
	public static boolean SharedGetLogin(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Splash", 0);
		if (sp.contains("SP_STATUS")) {
			return sp.getBoolean("SP_STATUS", true);
		}
		return true;
	}

	/**
	 * 存储首页显示标志
	 */
	public static void putFirstPic(Context context, boolean is_show) {
		SharedPreferences sp = context.getSharedPreferences("Splash", 0);
		Editor editor = sp.edit();
		editor.putBoolean("Start1", is_show);
		editor.commit();
	}

	/**
	 * 获取首页显示标志
	 */
	public static boolean getFirstPic(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Splash", 0);
		if (sp.contains("Start1")) {
			return sp.getBoolean("Start1", true);
		}
		return true;
	}

	/**
	 * 存储节目显示标志
	 */
	public static void putInterViewPic(Context context, boolean is_show) {
		SharedPreferences sp = context.getSharedPreferences("Splash", 0);
		Editor editor = sp.edit();
		editor.putBoolean("Start2", is_show);
		editor.commit();
	}

	/**
	 * 获取节目显示标志
	 */
	public static boolean getInterViewPic(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Splash", 0);
		if (sp.contains("Start2")) {
			return sp.getBoolean("Start1", true);
		}
		return true;
	}

	/**
	 * 存储大型直播首页显示标志
	 */
	public static void putRealAvPic(Context context, boolean is_show) {
		SharedPreferences sp = context.getSharedPreferences("Splash", 0);
		Editor editor = sp.edit();
		editor.putBoolean("Start3", is_show);
		editor.commit();
	}

	/**
	 * 获取大型直播首页显示标志
	 */
	public static boolean getRealAvPic(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Splash", 0);
		if (sp.contains("Start3")) {
			return sp.getBoolean("Start3", true);
		}
		return true;
	}

	/**
	 * 存储我的显示标志
	 */
	public static void putMyPic(Context context, boolean is_show) {
		SharedPreferences sp = context.getSharedPreferences("Splash", 0);
		Editor editor = sp.edit();
		editor.putBoolean("Start4", is_show);
		editor.commit();
	}

	/**
	 * 获取我的显示标志
	 */
	public static boolean getMyPic(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Splash", 0);
		if (sp.contains("Start4")) {
			return sp.getBoolean("Start4", true);
		}
		return true;
	}

	/**
	 * 存储闪光灯状态
	 */
	public static void PutLightStatus(Context context, boolean is_call) {
		SharedPreferences sp = context.getSharedPreferences("Light", 0);
		Editor editor = sp.edit();
		editor.putBoolean("Light_S", is_call);
		editor.commit();
	}

	/**
	 * 获取闪光灯状态
	 */
	public static boolean GetLightStatus(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Light", 0);
		if (sp.contains("Light_S")) {
			return sp.getBoolean("Light_S", true);
		}
		return true;
	}

	/**
	 * 存储紧急呼叫号码
	 */
	public static void SaveCallNumberStatus(Context context, String is_call) {
		SharedPreferences sp = context.getSharedPreferences("Call_Num", 0);
		Editor editor = sp.edit();
		editor.putString("number", is_call);
		editor.commit();
	}

	/**
	 * 获取紧急呼叫号码
	 */
	public static String GetCallNumberStatus(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Call_Num", 0);
		if (sp.contains("number")) {
			return sp.getString("number", "");
		}
		return null;
	}
}
