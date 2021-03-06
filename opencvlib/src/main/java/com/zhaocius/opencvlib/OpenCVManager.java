package com.zhaocius.opencvlib;

/**
 * Created by zhaozi on 2021/1/14.
 */
public class OpenCVManager {
    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();
    public native byte[] Bitmap2Grey(byte[] pixels,int w,int h);
}
