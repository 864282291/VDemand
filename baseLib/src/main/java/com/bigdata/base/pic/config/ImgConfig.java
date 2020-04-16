package com.bigdata.base.pic.config;

import com.bigdata.base.pic.bean.ImageFolder;

public class ImgConfig {

    // 最多选择图片的个数
    public static int MAX_NUM = 10;
    public static final int TAKE_PICTURE = 520;
    public static final int RESULT_IMG = MAX_NUM + 1;

    public static ImageFolder currentImageFolder = new ImageFolder();
    public static ImageFolder imageAll = new ImageFolder();


}
