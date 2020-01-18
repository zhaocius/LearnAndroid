package com.zhaozi.jumper;

import android.app.Application;
import android.content.Context;

import com.zhaozi.jumper.presenter.ImagePresenter;
import com.zhaozi.jumper.presenter.MainPresenter;


/**
 * Created by apple on 18/1/13.
 */

public class MyApplication extends Application {
    public static MainPresenter mainPresenter;
    public static ImagePresenter imagePresenter;
    public static Context context;

    public static MainPresenter getMainPresenter(){
        return mainPresenter;
    }
    public static ImagePresenter getImagePresenter(){
        return imagePresenter;
    }
    public static Context getContext(){return context;}
    @Override
    public void onCreate() {
        super.onCreate();
        mainPresenter=new MainPresenter();
        imagePresenter=new ImagePresenter();
        context=this;
    }
}
