package com.zhaocius.boardtest.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zhaocius.boardtest.application.BoardApplication;
import com.zhaocius.boardtest.myinterface.PenToolViewPresenter;

/**
 * Created by zhaozi on 2017/6/2.
 */

public class PenToolView extends ImageView implements PenToolViewPresenter.PenToolViewPresenterUI {
    private float mTouchX;
    private float mTouchY;
    private float x;
    private float y;
    private float mStartX;
    private float xx;
    private float mStartY;
    private static final String TAG = "PenToolView";
    private OnClickListener mOnClickListener;
    private WindowManager mWindowManager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    //此windowmanagerparams变量为获取的全局变量，用于保存悬浮笔工具的属性
    public WindowManager.LayoutParams mWindowParams = ((BoardApplication) getContext().getApplicationContext()).getWindowParams();


    private PenToolViewPresenter.PenToolViewPresenterUICallback mCallback;
    public PenToolView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        x = motionEvent.getRawX();//获取相对屏幕的坐标，悬浮窗所在的位置
        y = motionEvent.getRawY() - statusBarHeight;//statusbarheight是系统状态栏的高度
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = motionEvent.getX();
                mTouchY = motionEvent.getY();
                mStartX = x;
                mStartY = y;
                mCallback.call();
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                mTouchX = mTouchY = 0;
                if ((x - mStartX) < 5 && (y - mStartY) < 5) {
                    Log.e(TAG, "onclick执行了");
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(this);
                    }
                }
                break;
        }
        return true;  //注意false和true的区别。返回true上级就不再执行


    }

    @Override
    public void setOnClickListener(OnClickListener onclickListener) {
        this.mOnClickListener = onclickListener;

    }

    public void updateViewPosition() {   //更新位置

        mWindowParams.x = (int) (x - mTouchX);
        mWindowParams.y = (int) (y - mTouchY);
        //首先更新按钮位置
        mWindowManager.updateViewLayout(this, mWindowParams);
    }


    @Override
    public void setCallback(PenToolViewPresenter.PenToolViewPresenterUICallback callback) {
        mCallback=callback;
    }

    @Override
    public int getID() {
        return 1;
    }
}




