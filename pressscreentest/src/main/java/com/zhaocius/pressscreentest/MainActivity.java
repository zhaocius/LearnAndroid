package com.zhaocius.pressscreentest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTaskservice(this);
        finish();
    }



    private void startTaskservice(Context context){
        Intent taskIntent = new Intent(context,ControlService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(taskIntent);
        }else {
            context.startService(taskIntent);
        }
    }
}
