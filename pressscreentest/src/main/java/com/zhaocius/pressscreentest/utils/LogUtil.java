/**
 * @Package tv.hitv.android.smarttv.util 
 * @Description
 *
 * Copyright (c) Inspur Group Co., Ltd. Unpublished
 *
 * Inspur Group Co., Ltd.
 * Proprietary & Confidential
 *
 * This source code and the algorithms implemented therein constitute
 * confidential information and may comprise trade secrets of Inspur
 * or its associates, and any use thereof is subject to the terms and
 * conditions of the Non-Disclosure Agreement pursuant to which this
 * source code was originally received.
 */
package com.zhaocius.pressscreentest.utils;

import android.util.Log;

/**
 * @ClassName LogUtil
 * @Description
 * @Date 2013-1-30
 * @Email
 * @Author
 * @Version V1.0
 * modify by libaocheng
 */
public class LogUtil {

    private static final boolean DEBUG = true;
    private static final int STACK_TRACE_INDEX_START = 3;
    private static final String COLON_SEPARATOR = ": ";
    private static final String TAG = "NetControl";

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(TAG, tag + COLON_SEPARATOR + msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(TAG, tag + COLON_SEPARATOR + msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(TAG, tag + COLON_SEPARATOR + msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(TAG, tag + COLON_SEPARATOR + msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.w(tag, msg, tr);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (DEBUG) {
            Log.w(tag, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(TAG, tag + COLON_SEPARATOR + msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.e(tag, msg, tr);
        }
    }

    public static final void dumpStack(String tag) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder stackTrace = new StringBuilder();
        stackTrace.append("----- Dumping current stack ------\n");
        for (int index = STACK_TRACE_INDEX_START; index < stackTraceElements.length; index++) {
            StackTraceElement stackTraceElement = stackTraceElements[index];
            stackTrace.append("\t");
            stackTrace.append(stackTraceElement.getClassName());
            stackTrace.append(".");
            stackTrace.append(stackTraceElement.getMethodName());
            stackTrace.append(COLON_SEPARATOR);
            stackTrace.append(stackTraceElement.getLineNumber());
            stackTrace.append("\n");
        }
        stackTrace.append("----- End dumping current stack -----\n");
        Log.d(tag, stackTrace.toString());
    }

    /**
     * we can set log.tag.tag DEBUG to show log
     * 
     * @param tag
     * @param log
     */
    public static void logd(String tag, String log) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, tag+COLON_SEPARATOR+ log);
        }
    }
}
