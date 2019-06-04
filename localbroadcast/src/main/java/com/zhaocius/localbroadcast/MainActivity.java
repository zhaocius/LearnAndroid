package com.zhaocius.localbroadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=new Intent("com.zhaozi.test");
        intent.setClass(getApplicationContext(),MyIntentServce.class);
        startService(intent);
        IntentFilter filter=new IntentFilter("com.zhaozi.localtest");
        LocalBroadcastManager.getInstance(this).registerReceiver(guestReceiver, filter);

    }

    private BroadcastReceiver guestReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.zhaozi.localtest")){
                Log.d(TAG,"hahahaha");
            }
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(guestReceiver);

    }
}
