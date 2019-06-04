package com.zhaocius.aidltest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    IBinderTest mTestBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, BinderService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
        initButton();
    }

    private void initButton() {
        findViewById(R.id.bt_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mTestBinder.setName("xw");
                    Log.e("xw", "MainActivity---setName---xw");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.bt_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("xw", "MainActivity---getName---" + mTestBinder.getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ServiceConnection conn = new ServiceConnection() {
        /** 获取服务对象时的操作 */
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            Log.e("MainActivity", service.getClass().getName());
            mTestBinder = IBinderTest.Stub.asInterface(service);
        }

        /** 无法获取到服务对象时的操作 */
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub

        }
    };

}
