package com.zhaozi.jumper.presenter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.zhaozi.jumper.MyApplication;
import com.zhaozi.jumper.util.Utils;
import com.zhaozi.jumper.view.Scream;

/**
 * Created by apple on 18/1/24.
 */

public class ImagePresenter extends BasePresenter<ImagePresenter.BgImageViewUI,ImagePresenter.ImagePresenterCallback> {
    private Paint paint;
    private long downtime;
    private boolean canTouch;
    private Scream scream;
    public void setScream(Scream scream){
        this.scream=scream;
    }

    public void setCanTouch(boolean canTouch) {
        this.canTouch = canTouch;
    }




    @Override
    protected ImagePresenter.ImagePresenterCallback createUC(BgImageViewUI myImageViewUI) {
        return new ImagePresenter.ImagePresenterCallback() {

            @Override
            public void onDraw(Canvas canvas) {
                canvas.drawCircle(Utils.getInstance().getPoints()[0].x,Utils.getInstance().getPoints()[0].y,100,paint);
                changeColor();
                canvas.drawCircle(Utils.getInstance().getPoints()[1].x,Utils.getInstance().getPoints()[1].y,100,paint);
                changeColor();

            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                if(canTouch){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            scream.startScream();
                            downtime = System.currentTimeMillis();
                            break;

                        case MotionEvent.ACTION_UP:
                            long uptime = System.currentTimeMillis();
                            Log.d("zztest", "tellActivityTime" + (uptime - downtime));
                            MyApplication.getMainPresenter().tellActivityTime(uptime - downtime);
                            scream.stopScream();
                            break;

                    }
                }
                return true;
            }
        };
    }

    public void invalidateSelf() {
        getUI().invalidateSelf();
    }

    public interface ImagePresenterCallback{
         void onDraw(Canvas canvas);
         boolean onTouchEvent(MotionEvent event);

    }

    public interface BgImageViewUI extends BasePresenter.UI<ImagePresenter.ImagePresenterCallback>{

        void invalidateSelf();
    }

    public void onInited(){
        paint=new Paint();
        paint.setColor(Color.BLUE);
        downtime=0;
        canTouch=true;
        invalidateSelf();
    }

    private void changeColor(){
        if(paint.getColor()==Color.BLUE){
            paint.setColor(Color.BLACK);
        }else{
            paint.setColor(Color.BLUE);
        }
    }

}
