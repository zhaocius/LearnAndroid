package com.zhaozi.jumper.util;

import android.app.Application;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.media.MediaPlayer;

import com.zhaozi.jumper.MyApplication;
import com.zhaozi.jumper.view.Scream;

import java.io.IOException;

/**
 * Created by apple on 18/1/20.
 */

public class Utils implements Scream {
    private static Utils utils;
    private  Point[] pointPos=new Point[3];
    public static Utils getInstance(){
        if(utils==null)
            utils= new Utils();
        return utils;
    }
    private Utils(){
        initPoints();

        try {
            player = new MediaPlayer();
            assetManager = MyApplication.getContext().getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd("b.mp3");
            player.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),
                    fileDescriptor.getStartOffset());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void initPoints(){
        pointPos[0]=new Point(150,150);
        pointPos[1]=new Point(500,500);
        pointPos[2]=new Point(150,150);
    }

    public  double calculateDis(Point point1, Point point2){
        return Math.sqrt(Math.pow((point1.x-point2.x),2)+Math.pow((point1.y-point2.y),2));
    }
    public Point[] getPoints(){
        return this.pointPos;
    }

    private MediaPlayer player = null;
    private AssetManager assetManager;
    @Override
    public void startScream() {
        try{
            player.prepare();
            player.start();

        } catch(Exception e){

        }
    }

    @Override
    public void stopScream() {
        try{
            player.stop();
        } catch(Exception e){

        }
    }
}
