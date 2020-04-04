package com.zhaozi.jumper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhaozi.jumper.database.DataManager;
import com.zhaozi.jumper.presenter.MainPresenter;
import com.zhaozi.jumper.util.Utils;
import com.zhaozi.jumper.view.BgImageView;
import com.zhaozi.jumper.view.MoveImageView;

public class MainActivity extends Activity implements MainPresenter.MainActivityUI{

    int score=0;
    private String text;
    AlertDialog.Builder normalDialog;
    MainPresenter.MainPresenterCallback mainPresenterCallback;
    private BgImageView mBgImageView;
    private FrameLayout mFrameLayout;
    private MoveImageView mMoveImageView;
    private TextView mCurrentScore,mHighScore;
    private FrameLayout.LayoutParams moveImageParams=new FrameLayout.LayoutParams(100,100);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        MyApplication.getMainPresenter().init(this);
        MyApplication.getImagePresenter().init(mBgImageView);
        MyApplication.getImagePresenter().setScream(Utils.getInstance());
    }

    private void initViews(){
        mFrameLayout=findViewById(R.id.framelayout);
        mBgImageView=findViewById(R.id.imageview);
        mBgImageView.setBackgroundColor(Color.YELLOW);
        mCurrentScore=findViewById(R.id.current_score);
        mHighScore=findViewById(R.id.high_score);
        mHighScore.setText(""+DataManager.getInstance().getHighScore());
        mCurrentScore.setText(""+score);
        mMoveImageView=new MoveImageView(this);
        moveImageParams.leftMargin=100;
        moveImageParams.topMargin=100;
        mMoveImageView.setLayoutParams(moveImageParams);
        mMoveImageView.setBackground(getDrawable(R.mipmap.ln));
        mFrameLayout.addView(mMoveImageView);


    }



    @Override
    public void setPresenterCallback(MainPresenter.MainPresenterCallback mainPresenterCallback) {
        this.mainPresenterCallback=mainPresenterCallback;
    }

    @Override
    public void  moveLN(final int moveX,final int moveY) {

        TranslateAnimation animation=new TranslateAnimation(0,moveX,0,moveY);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(500);
        animation.setStartOffset(0);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                MyApplication.getImagePresenter().setCanTouch(false);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                moveImageParams.leftMargin=mMoveImageView.getLeft()+moveX;
                moveImageParams.topMargin=mMoveImageView.getTop()+moveY;
                mMoveImageView.clearAnimation();
                mMoveImageView.setLayoutParams(moveImageParams);
                if(mainPresenterCallback.checkPosition()){
                    score++;
                    mainPresenterCallback.refreshBg();
                    MyApplication.getImagePresenter().setCanTouch(true);
                    mCurrentScore.setText(""+score);
                }else{

                    int highScore= DataManager.getInstance().getHighScore();
                    if(score<=highScore){
                        text="太遗憾了，您还差 "+(highScore-score)+" 分破纪录";
                    }else{
                        text="恭喜您破记录";
                        Log.d("zz1","  "+score);
                        DataManager.getInstance().setHighScore(score);
                    }
                    score=0;
                    showNormalDialog();

                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mMoveImageView.startAnimation(animation);

    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.mipmap.ic_launcher);
        normalDialog.setTitle(text);
        normalDialog.setMessage("重新开始?");
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveImageParams.leftMargin=100;
                        moveImageParams.topMargin=100;
                        mMoveImageView.setLayoutParams(moveImageParams);
                        Utils.getInstance().initPoints();
                        MyApplication.getMainPresenter().onInited();
                        MyApplication.getImagePresenter().onInited();
                        mCurrentScore.setText(""+score);

                    }
                });
        normalDialog.setNegativeButton("退出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                });
        // 显示
        normalDialog.show();
    }

}
