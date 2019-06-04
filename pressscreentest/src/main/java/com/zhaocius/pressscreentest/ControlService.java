package com.zhaocius.pressscreentest;

import android.app.Instrumentation;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import com.zhaocius.pressscreentest.utils.LogUtil;

public class ControlService extends Service {
    private static final String TAG = ControlService.class.getSimpleName();

    public static final String CHANNEL_ID_STRING = "netControl";
    private Context mContext;
    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "onCreate");
        mContext = this;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        setServcieForeground();
    }

    /**
     * <p>Title: setServcieForeground.</p>
     * <p>Description: 设置为前台进程.</p>
     */
    private void setServcieForeground() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_STRING, "netcontrol", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(mContext, CHANNEL_ID_STRING).build();
            startForeground(1, notification);
        } else {
            Notification notification = new Notification();
            notification.contentView = null;
            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG, "onStartCommand");
         new ThreadClass(440,660).start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG, "onDestroy");
        stopForeground(true);
        if (mNotificationManager != null) {
            mNotificationManager.cancel(1);
        }
        super.onDestroy();
    }

    public class ThreadClass extends Thread {
        private int x, y;

        //400,689
        @Override
        public void run() {
            int i=0;
            while (true) {
                i++;
                if(i==50){
                    try {
                        Thread.sleep(2500);
                        i=0;
                    }catch (Exception e){

                    }
                }
                // 可以不用在 Activity 中增加任何处理，各 Activity 都可以响应
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_DOWN, x, y, 0));
                    inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_UP, x, y, 0));
                    Log.d("点击位置", x + "," + y);
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
                //线程睡眠3s
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

        public ThreadClass(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}