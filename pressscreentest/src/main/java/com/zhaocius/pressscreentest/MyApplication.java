package com.zhaocius.pressscreentest;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.zhaocius.pressscreentest.utils.ANRError;
import com.zhaocius.pressscreentest.utils.ANRWatchDog;
import com.zhaocius.pressscreentest.utils.LogUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private Context mContext;
    private static Context mAppContext;
    @Override
    public void onCreate() {
        mContext = this;
        mAppContext = this;
        super.onCreate();
        LogUtil.d(TAG, "this is build in 2019-01-25");
        new ANRWatchDog(15*1000).setANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAppNotResponding(ANRError error) {
                // Handle the error.
                LogUtil.d(TAG, "service is Not Responding enter!!!"+error.getMessage());
                error.printStackTrace();
                android.os.Process.killProcess(android.os.Process.myPid());
                startTaskservice(mContext);
            }
        }).start();
//        new ExceptionHandler(mContext).setFCListener(new ExceptionHandler.FCListener() {
//
//            @Override
//            public void onFCDispose(Throwable paramThrowable) {
//                LogUtil.d(TAG, "onFCListerner enter!!!");
//                paramThrowable.printStackTrace();
//            }
//        });
    }

    private void startTaskservice(Context context){
        Intent taskIntent = new Intent(context,ControlService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(taskIntent);
        }else {
            context.startService(taskIntent);
        }
    }
    /**
     * 
     * <p>Title: TODO.</p>
     * <p>Description: TODO.</p>
     * 
     * @return
     */
    private long getcpuinfo() {
        String[] cpuInfos = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                    "/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        } catch (IOException ex) {
            Log.e(TAG, "IOException" + ex.toString());
            return 0;
        }
        long totalCpu = 0;
        try {
            totalCpu = Long.parseLong(cpuInfos[2]) + Long.parseLong(cpuInfos[3])
                    + Long.parseLong(cpuInfos[4]) + Long.parseLong(cpuInfos[6])
                    + Long.parseLong(cpuInfos[5]) + Long.parseLong(cpuInfos[7])
                    + Long.parseLong(cpuInfos[8]);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.i(TAG, "ArrayIndexOutOfBoundsException" + e.toString());
            return 0;
        }
        return totalCpu;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    
    
    public static Context getAppContext() {
        return mAppContext;
    }
}
