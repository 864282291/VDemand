package com.bigdata.base.pic.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bigdata.base.R;
import com.bigdata.base.pic.view.glide.GlideCircleTransform;
import com.bigdata.base.pic.view.glide.GlideRoundTransform;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;

/**
 * @auther Leo--李智
 * Create at 2017/5/12 14:06
 * Glide图片处理类
 */
public class GlideImgHelper {

    private static Context mContext;
    private static GlideImgHelper glideImgHelper = null;
    private int errorImg = R.mipmap.ic_launcher;
    private int placeImg = R.mipmap.ic_launcher;
//    public static final String IMAGE_BASE_URL = "http://guoxue.xuancode.com";
//    private String BASE_IMG_URL = "";

    private static class SingleImgHolder {
        /**
         * 单例对象实例
         */
        static final GlideImgHelper INSTANCE = new GlideImgHelper();
    }

    public static GlideImgHelper getInstance(Context context) {
        mContext = context;
        return SingleImgHolder.INSTANCE;
    }

    public DrawableRequestBuilder<String> getBaseGlideInstance(String url) {
        return Glide.with(mContext).load(url)
                .crossFade(10)
                .diskCacheStrategy(DiskCacheStrategy.RESULT);
    }

    public DrawableRequestBuilder<String> getDefaultErrorGlideInstance(String url) {
        return getBaseGlideInstance(url)
                .placeholder(errorImg)
                .error(errorImg);
    }

    public DrawableRequestBuilder<String> getErrorGlideInstance(String url, int errorImage) {
        return getBaseGlideInstance(url)
                .placeholder(errorImage)
                .error(errorImage);
    }

    public void showImg(ImageView imageView, DrawableRequestBuilder<String> builder) {
        if (mContext == null)
            throw new NullPointerException("Context不能为空");
        try {
            if (Util.isOnMainThread()) {
                builder.into(imageView);
            }
        } catch (Exception e) {
            Log.e("eee", e.getMessage());
        }
    }

    /**
     * 加载普通图片
     *
     * @param url             图片网址
     * @param imageView       当前控件
     * @param requestListener 返回监听
     */
    public void showNormalImg(String url, ImageView imageView, RequestListener requestListener) {
        showImg(imageView, getBaseGlideInstance(url).listener(requestListener));
    }

    public void showNormalImg(String url, ImageView imageView, int errorImage, RequestListener requestListener) {
        showImg(imageView, getErrorGlideInstance(url, errorImage));
    }

    /**
     * 加载普通图片
     *
     * @param url       图片网址
     * @param imageView 当前控件
     */
    public void showNormalImg(String url, ImageView imageView) {
        showImg(imageView, getDefaultErrorGlideInstance(url));
    }

    public void showNormalImg(String url, ImageView imageView, int errorImage) {
        showImg(imageView, getErrorGlideInstance(url, errorImage));
    }

    /**
     * 加载圆角图片
     *
     * @param url       图片网址
     * @param imageView 当前控件
     */
    public void showRoundImg(String url, ImageView imageView) {
        showImg(imageView, getDefaultErrorGlideInstance(url).transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 10)));
    }

    public void showRoundImg(String url, ImageView imageView, int errorImage) {
        showImg(imageView, getErrorGlideInstance(url, errorImage).transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 10)));
    }

    /**
     * 加载圆形图片
     *
     * @param url       图片网址
     * @param imageView 当前控件
     */
    public void showCircleImg(String url, ImageView imageView) {
        showImg(imageView, getDefaultErrorGlideInstance(url).transform(new GlideCircleTransform(mContext)));
    }

    public void showCircleImg(String url, ImageView imageView, int errorImage) {
        showImg(imageView, getErrorGlideInstance(url, errorImage).transform(new GlideCircleTransform(mContext)));
    }

//    /**
//     * 加载图片-高斯模糊
//     *
//     * @param url       加载路径
//     * @param imageView 加载图片
//     */
//    public void showBlurImg(String url, ImageView imageView) {
//        if (mContext == null) {
//            Log.e("加载错误", "上下文环境错误");
//            return;
//        }
//        if (!TextUtils.isEmpty(url)) {
//            if (!url.startsWith("http") && !url.startsWith("https") && !url.startsWith("file:")) {
//                if (url.startsWith("/Upload/")) {
//                    url = IMAGE_BASE_URL + url;
//                } else {
//                    url = "file://" + url;
//                }
//            }
//        }
//        if (Util.isOnMainThread()) {
//            Glide.with(mContext).load(url)// 加载图片
//                    .error(errorImg)// 设置错误图片
//                    .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
//                    .placeholder(errorImg)// 设置占位图
//                    .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
//                    .into(imageView);
//        }
//
//    }


    /**
     * 清除图片内存缓存
     */
    public void clearMemory() {
        Glide.get(mContext).clearMemory();
    }

}
