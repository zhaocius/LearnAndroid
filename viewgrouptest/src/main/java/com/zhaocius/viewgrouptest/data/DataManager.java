package com.zhaocius.viewgrouptest.data;

import java.util.ArrayList;

/**
 * Created by zhaozi on 18-1-18.
 */

public class DataManager {
    private static DataManager dataManager=null;
    private static ArrayList<Data> datas=new ArrayList<Data>();

    private DataManager(){
        initDatas();
    }
    public static synchronized DataManager getInstance(){
        if(dataManager==null){
            return new DataManager();
        }else{
            return dataManager;
        }
    }
    private void initDatas(){
        for(int i=0;i<3;i++) {
            Data temp = new Data();
            temp.setId(i);
            temp.setShown(true);
            temp.setText(String.valueOf((char)('a'+i)));
            temp.setImageSource(android.R.drawable.ic_delete);
            temp.setPressedImageSource(android.R.drawable.ic_input_delete);
            datas.add(temp);
        }
    }
    public  ArrayList<Data> getDatas(){
        return datas;
    }


}
