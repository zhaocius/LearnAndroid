package com.zhaozi.jumper.presenter;

import android.graphics.Point;
import android.util.Log;
import com.zhaozi.jumper.MyApplication;
import com.zhaozi.jumper.util.Utils;

import java.util.Random;

/**
 * Created by apple on 18/1/13.
 */

public class MainPresenter extends BasePresenter<MainPresenter.MainActivityUI,MainPresenter.MainPresenterCallback> {
    private boolean toSecond;
    private Random random;
    int width,height;


    @Override
    protected MainPresenterCallback createUC(MainActivityUI mainActivity) {
        return new MainPresenterCallback() {

            @Override
            public boolean checkPosition() {
                Log.d("zztest"," 设置后的moveImage的x "+Utils.getInstance().getPoints()[2].x);
                return Utils.getInstance().calculateDis(Utils.getInstance().getPoints()[toSecond?1:0],Utils.getInstance().getPoints()[2])<50;
            }

            @Override
            public void refreshBg(){
                int tempx,tempy;
                do {
                    tempx = random.nextInt(width - 200)+100;
                    tempy=  random.nextInt(height - 200)+100;
                }while(Utils.getInstance().calculateDis(new Point( tempx,tempy),Utils.getInstance().getPoints()[toSecond?1:0])<300);
                Utils.getInstance().getPoints()[toSecond?0:1].set(tempx,tempy);
                Log.d("zztest","xin point" +Utils.getInstance().getPoints()[0]);
                MyApplication.getImagePresenter().invalidateSelf();
                toSecond=toSecond?false:true;

            }
        };
    }

    public void tellActivityTime(long time) {
        int x1,x2,y1,y2;
        x1 = Utils.getInstance().getPoints()[2].x;
        y1 = Utils.getInstance().getPoints()[2].y;
        x2 = toSecond?Utils.getInstance().getPoints()[1].x:Utils.getInstance().getPoints()[0].x;
        y2 = toSecond?Utils.getInstance().getPoints()[1].y:Utils.getInstance().getPoints()[0].y;
        int dx=x2-x1;
        int dy=y2-y1;

        double dis= Utils.getInstance().calculateDis(Utils.getInstance().getPoints()[0],Utils.getInstance().getPoints()[1]);
        final int tempx=(int)((dx/dis)*time);
        final int tempy=(int)((dy/dis)*time);
        Log.d("zztest","dx"+dx+"dy"+dy+"dis"+dis+"tempx"+tempx+"tempy"+tempy+"x1"+x1);
        Utils.getInstance().getPoints()[2].set(tempx+x1,tempy+y1);
        getUI().moveLN(tempx,tempy);




    }

    public interface MainPresenterCallback{
       boolean checkPosition();

        void refreshBg();
    }

    public interface MainActivityUI extends BasePresenter.UI<MainPresenterCallback>{
        void moveLN(int moveX,int moveY);
    }

    public void onInited(){
        toSecond=true;
       random=new Random();
        width=MyApplication.getContext().getResources().getDisplayMetrics().widthPixels;
        height=MyApplication.getContext().getResources().getDisplayMetrics().widthPixels;

    }

}
