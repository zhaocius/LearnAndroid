package com.zhaocius.pressscreentest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.zhaocius.pressscreentest.ControlService;
import com.zhaocius.pressscreentest.MyApplication;
import com.zhaocius.pressscreentest.utils.LogUtil;

public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            LogUtil.d(TAG, "system boot....... start net control");
            startTaskservice(context);
        }else if(intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)||
                intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)){
            String packageName = intent.getDataString().substring(8);
            LogUtil.d(TAG, "install packagename is "+packageName);
            if ((MyApplication.getAppContext().getPackageName().equals(packageName))){
                startTaskservice(context);
            }
        }
    }
    private void startTaskservice(Context context){
        Intent taskIntent = new Intent(context, ControlService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(taskIntent);
        }else {
            context.startService(taskIntent);
        }
    }
}
