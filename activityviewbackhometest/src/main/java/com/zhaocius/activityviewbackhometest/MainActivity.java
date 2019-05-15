package com.zhaocius.activityviewbackhometest;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class MainActivity extends Activity {
    private static final String TAG = "zzMainActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "onCreate");
    }

    /**
     * 从onStop回到Activity的时候会执行 
     * 按HOME键的时候会执行onStop,重新回到程序会执行这个方法 
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "onRestart");
    }

    /**
     * 在onCreate,onRestart后面执行 
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");
    }

    /**
     * 在onStart后面执行，执行这个方法后这个Activity就处于全部Activity堆栈的最上面 
     * 进入用户可见可操作的状态 
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }

    /**
     * 当其他Activity启动时这个方法会执行 
     * 按HOME和BACK都会执行这个方法 
     * 最好在这个方法中提交或者保存数据，因为很有可能再也不会回到这个activity中。 
     * 这个方法最好不要执行太长时间，因为下个activity开始执行前会等待这个方法返回。 
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    /**
     * activity很久没被显示，要被销毁，系统资源缺乏，都会调用这个方法 
     * 按HOME和BACK都会执行这个方法 
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
    }

    /**
     * 调用finish方法，或者系统回收资源时调用 
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

}
