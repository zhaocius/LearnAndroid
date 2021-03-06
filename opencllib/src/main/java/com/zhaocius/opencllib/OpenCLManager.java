package com.zhaocius.opencllib;

/**
 * Created by zhaozi on 2021/1/14.
 */
public class OpenCLManager {
    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();
    public native int openCLTest();
}
