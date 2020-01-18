package com.zhaocius.boardtest.application;

import android.app.Application;
import android.util.Log;
import android.view.WindowManager;

import com.zhaocius.boardtest.myinterface.MainPresenter;

/**
 * Created by zhaozi on 2017/6/2.
 */

public class BoardApplication extends Application {
    private static final String TAG = "BoardApplication";
    private WindowManager.LayoutParams mWindowParams=new WindowManager.LayoutParams();
    public WindowManager.LayoutParams getWindowParams(){
        return mWindowParams;
    }
    private static MainPresenter mMainPresenter=null;
    @Override
    public void onCreate(){
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        mMainPresenter=new MainPresenter();
        mMainPresenter.init();
    }

    public static MainPresenter getMainPresenter(){
        return mMainPresenter;
    }
}
