package com.zhaocius.localbroadcast;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyIntentServce extends IntentService {
    private static final String TAG = "MyIntentServce";

    public MyIntentServce() {
        super("DownLoadService");
    }


    @Override
    public int onStartCommand(Intent intent,int flags,int startid){
        return super.onStartCommand(intent,flags,startid);

    }
    @Override
    protected void onHandleIntent(Intent intent) {
       if(intent.getAction().equals("com.zhaozi.test")){
           Log.d(TAG, "onHandleIntent: ");
           try {
               Thread.sleep(3 * 1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           

           Intent localTest=new Intent("com.zhaozi.localtest");
           LocalBroadcastManager.getInstance(this).sendBroadcast(localTest);


       }
    }
}
