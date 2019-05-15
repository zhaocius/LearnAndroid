package com.zhaocius.activityviewbackhometest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by zhaozi on 29/08/2017.
 */

public class MyTextView extends TextView {
    private static final String TAG = "zzMyTextView";
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context,AttributeSet attrs) {
        super(context, attrs);
    }
@Override
    public void onFinishInflate(){
    Log.d(TAG, "onFinishInflate: ");
        super.onFinishInflate();
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus){
        Log.d(TAG, "onWindowFocusChanged: ");
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    public void onAttachedToWindow(){
        Log.d(TAG, "onAttachedToWindow: ");
        super.onAttachedToWindow();
    }
    @Override
    public void onDetachedFromWindow(){
        Log.d(TAG, "onDetachedFromWindow: ");
        super.onDetachedFromWindow();
    }
    @Override
    public void onWindowVisibilityChanged(int visibility){
        Log.d(TAG, "onWindowVisibilityChanged: ");
        super.onWindowVisibilityChanged(visibility);
    }
    
}
