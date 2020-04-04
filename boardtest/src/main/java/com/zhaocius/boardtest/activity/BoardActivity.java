package com.zhaocius.boardtest.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.zhaocius.boardtest.application.BoardApplication;
import com.zhaocius.boardtest.myinterface.MainPresenter;
import com.zhaocius.boardtest.view.BoardView;
import com.zhaocius.boardtest.view.PenToolView;
import com.zhaocois.boardtest.R;

public class BoardActivity extends Activity implements MainPresenter.MainPresenterUI {

    private MainPresenter.MainPresenterUICallback mCallback;
    private RelativeLayout allLayout;
    private PenToolView mPenToolView;
    private BoardView mBoardView;
    private WindowManager wm;
    private static final String TAG = "BoardActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        allLayout=findViewById(R.id.layout_all);
        wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        initBoardView();
        initPentoolView();
        attachUis();
        IntentFilter mFinishIntentFilter= new IntentFilter();
        mFinishIntentFilter.addAction("com.zhaozi.meeting.exit");
        registerReceiver(mFinishReceiver,mFinishIntentFilter);
    }

    private BroadcastReceiver mFinishReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent==null)
                return;
            if(intent.getAction().equals("com.zhaozi.meeting.exit")){
                finishMeeting();
            }
        }
    };

    private void finishMeeting() {
        detachUis();
        unregisterReceiver(mFinishReceiver);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);

    }


    private void attachUis() {
        Log.d(TAG, "onAttachUis: ");
        BoardApplication.getMainPresenter().attachUI(this);
        BoardApplication.getMainPresenter().getPenToolViewPresenter().attachUI(mPenToolView);
        BoardApplication.getMainPresenter().getBoardViewPresenter().attachUI(mBoardView);
        Log.d(TAG, "attachUis: ");
    }

    private void detachUis(){
        BoardApplication.getMainPresenter().getPenToolViewPresenter().detachUI(mPenToolView);
        BoardApplication.getMainPresenter().getBoardViewPresenter().detachUI(mBoardView);
        BoardApplication.getMainPresenter().detachUI(this);

    }

    @Override
    public void onDestroy() {
        detachUis();
        unregisterReceiver(mFinishReceiver);
        super.onDestroy();
    }


    private void initBoardView() {
        mBoardView = new  BoardView(this);
        allLayout.addView(mBoardView, getLayoutParams(0, 0, wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight()));
    }

    private void initPentoolView() {
        mPenToolView = new PenToolView(this);
        mPenToolView.setImageResource(R.drawable.imagebutton); // 这里简单的用自带的icon来做演示
        allLayout.addView(mPenToolView, getLayoutParams(wm.getDefaultDisplay().getWidth()-500, wm.getDefaultDisplay().getHeight()-500, 90, 90));
        mPenToolView.setOnPostionChangeListener(new PenToolView.OnPostionChangeListener() {
            @Override
            public void onPositionChanged(float x, float y) {
                allLayout.updateViewLayout(mPenToolView,getLayoutParams((int)x, (int)y, 90, 90));
            }
        });
        mPenToolView.setClickable(true);
        mPenToolView.setOnClickListener(new PenToolView.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.call();
                Intent intent =new Intent();
                intent.setAction("com.zhaozi.meeting.exit");
                BoardActivity.this.sendBroadcast(intent);

            }
        });
    }

    @Override
    public void setCallback(MainPresenter.MainPresenterUICallback callback) {
        mCallback = callback;
    }

    @Override
    public int getID() {
        return 0;
    }

    private RelativeLayout.LayoutParams getLayoutParams(int x, int y, int width, int height) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,height);
        lp.leftMargin = x;
        lp.topMargin = y;
        return lp;

    }

}

