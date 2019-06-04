package com.zhaocius.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class BinderService extends Service {

    private String name;

    public BinderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinderTest;
    }

    IBinderTest.Stub mBinderTest = new IBinderTest.Stub() {
        @Override
        public void setName(String name) throws RemoteException {
            BinderService.this.name = name;
            Log.e("xw", "BinderService---setName---" + name);
        }

        @Override
        public String getName() throws RemoteException {
            Log.e("xw", "BinderService---getName---" + name);
            return name;
        }
    };
}
