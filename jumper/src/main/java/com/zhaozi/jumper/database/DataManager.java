package com.zhaozi.jumper.database;

import android.content.SharedPreferences;

import com.zhaozi.jumper.MyApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by apple on 18/1/24.
 */

public class DataManager {
    private static DataManager dataManager;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private DataManager(){
        pref = MyApplication.getContext().getSharedPreferences("data",MODE_PRIVATE);
        editor= pref.edit();
    }
    public static DataManager getInstance(){
        if(dataManager==null){
            dataManager=new DataManager();

        }
        return dataManager;
    }

    public int getHighScore(){
        int name = pref.getInt("name",0);
        return name;

    }
    public void setHighScore(int score){
        editor.putInt("name",score);
        editor.commit();
    }

}
