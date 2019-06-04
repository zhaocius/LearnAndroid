/* 
 * Copyright (C) 2011 Hisense Electric Co., Ltd. 
 * All Rights Reserved.
 *
 * ALL RIGHTS ARE RESERVED BY HISENSE ELECTRIC CO., LTD. ACCESS TO THIS
 * SOURCE CODE IS STRICTLY RESTRICTED UNDER CONTRACT. THIS CODE IS TO
 * BE KEPT STRICTLY CONFIDENTIAL.
 *
 * UNAUTHORIZED MODIFICATION OF THIS FILE WILL VOID YOUR SUPPORT CONTRACT
 * WITH HISENSE ELECTRIC CO., LTD. IF SUCH MODIFICATIONS ARE FOR THE PURPOSE
 * OF CIRCUMVENTING LICENSING LIMITATIONS, LEGAL ACTION MAY RESULT.
 */

package com.zhaocius.pressscreentest.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title: TODO.
 * </p>
 * <p>
 * Description: TODO.
 * </p>
 * 
 * @author libaocheng(liuboyang@hisense.com) 2015-04-16.
 * @version $Id$
 */

public class ExceptionHandler implements UncaughtExceptionHandler {

    private static final String TAG = ExceptionHandler.class.getSimpleName();
    private static final boolean DEBUG = false;
    public Throwable mThrowable;

    public interface FCListener {
        public void onFCDispose(Throwable paramThrowable);
    }
    private FCListener mFCListener = DEFAULT_FC_LISTENER;
    private UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    private Map<String, String> mLogInfo = new HashMap<String, String>();
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH-mm-ss");

    private static final FCListener DEFAULT_FC_LISTENER = new FCListener() {

        @Override
        public void onFCDispose(Throwable paramThrowable) {
            Log.d(TAG, "onFCDispose");
            //handleException(paramThrowable);
            
        }
    };
    /**
     * 
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param context
     */
    public ExceptionHandler(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param paramThread
     * @param paramThrowable
     */
    public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
        if (!handleException(paramThrowable) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(paramThread, paramThrowable);
        } else {
            selfDisposeException(paramThread,paramThrowable);
        }
    }

    private void selfDisposeException(Thread paramThread,Throwable paramThrowable) {
        mFCListener.onFCDispose(paramThrowable);
		mDefaultHandler.uncaughtException(paramThread, paramThrowable);
        
    }

    public ExceptionHandler setFCListener(FCListener listener) {
        if (listener == null) {
            mFCListener = DEFAULT_FC_LISTENER;
        }
        else {
            mFCListener = listener;
        }
        return this;
    }

    /**
     * 
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: 自定义异常处理.
     * </p>
     * 
     * @param paramThrowable
     * @return
     */
    public boolean handleException(Throwable paramThrowable) {
        Log.d(TAG, "handleException:"+paramThrowable);
        if (paramThrowable == null)
            return false;
        getDeviceInfo(mContext);
        saveCrashLogToLocalFile(paramThrowable);
        
        return true;
    }

    /**
     * 
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: getDeviceInfo:{获取设备参数信息}.
     * </p>
     * 
     * @param paramContext
     */
    public void getDeviceInfo(Context paramContext) {
        try {
            PackageManager mPackageManager = paramContext.getPackageManager();
            PackageInfo mPackageInfo = mPackageManager.getPackageInfo(
                    paramContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (mPackageInfo != null) {
                String versionName = mPackageInfo.versionName == null ? "null"
                        : mPackageInfo.versionName;
                String versionCode = mPackageInfo.versionCode + "";
                mLogInfo.put("versionName", versionName);
                mLogInfo.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] mFields = Build.class.getDeclaredFields();
        for (Field field : mFields) {
            try {
                field.setAccessible(true);
                mLogInfo.put(field.getName(), field.get("").toString());
                if(DEBUG)
                    Log.d(TAG, field.getName() + ":" + field.get(""));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: saveCrashLogToFile:{将崩溃的Log保存到本地的/data/data/包名文件下}.
     * </p>
     * 
     * @param paramThrowable
     * @return
     */
    private String saveCrashLogToLocalFile(Throwable paramThrowable) {
        StringBuffer mStringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : mLogInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            mStringBuffer.append(key + "=" + value + "\r\n");
        }
        Writer mWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mWriter);
        paramThrowable.printStackTrace(mPrintWriter);
        paramThrowable.printStackTrace();
        Throwable mThrowable = paramThrowable.getCause();
        while (mThrowable != null) {
            mThrowable.printStackTrace(mPrintWriter);
            mPrintWriter.append("\r\n");
            mThrowable = mThrowable.getCause();
        }
        mPrintWriter.close();
        String mResult = mWriter.toString();
        mStringBuffer.append(mResult);
        String mTime = mSimpleDateFormat.format(new Date());
        String mFileName = "CrashLog-" + mTime + ".log";
        try {
            File mDirectory = null;
            if(CommonUtils.isSdcardExist()){
                mDirectory = new File(Environment.getExternalStorageDirectory()+ "/CrashInfos");
            }else{
                mDirectory = new File(mContext.getFilesDir() + "/CrashInfos");
            }
            long length = 0;
            try {
                length=getFileSizes(mDirectory);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(length>3921*2) {
                DeleteFile(mDirectory);
            }
            if (!mDirectory.exists())
                mDirectory.mkdir();
            FileOutputStream mFileOutputStream = new FileOutputStream(mDirectory + "/" + mFileName);
            mFileOutputStream.write(mStringBuffer.toString().getBytes());
            mFileOutputStream.close();
            return mFileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }
    
    
    /**
     * 
     * 
     * @param file
     * 
     */
    public void DeleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    DeleteFile(f);
                }
                file.delete();
            }
        }
    }
}
