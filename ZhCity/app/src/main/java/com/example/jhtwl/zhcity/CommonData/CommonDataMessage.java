package com.example.jhtwl.zhcity.CommonData;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/9/20  上午11:43
 */
public class CommonDataMessage {
    /**
     *  基本URl地址
     */
    public static final String BASE_URL = "http://10.10.0.113:8080/zhbj";
    /**
     * 获取分类信息的接口
     */
    public static final String CATEGORIES_URL = BASE_URL + "/categories.json";
    /**
     * 获取组图信息的接口
     */
    public static final String PHOTOS_URL = BASE_URL
            + "/photos/photos_1.json";
}
