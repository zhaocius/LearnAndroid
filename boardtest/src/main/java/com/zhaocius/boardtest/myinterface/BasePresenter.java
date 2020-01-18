package com.zhaocius.boardtest.myinterface;

import android.util.Log;

/**
 * Created by zhaozi on 16/08/2017.
 */

public abstract class BasePresenter {
    private static final String TAG = "BasePresenter";
    private boolean mInited;
    public final void init(){
        Log.d(TAG, "init: ");
        mInited=true;
        onInited();

    }
    public final void suspend(){
        onSuspended();
        mInited=false;
    }
    public final boolean isIniated(){
        return mInited;
    }
    protected  void onInited(){
        Log.d(TAG, "onInited: ");

    }
    protected  void onSuspended(){

    }
}
