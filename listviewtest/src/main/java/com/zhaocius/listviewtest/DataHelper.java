package com.zhaocius.listviewtest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaozi on 17-12-12.
 */

public class DataHelper {
    List<Data> datas=new ArrayList<Data>();
    private DataHelper(){
        initData();
    }

    private void initData() {
        datas.add(new Data(R.mipmap.ic_launcher,"a"));
        datas.add(new Data(R.mipmap.ic_launcher,"b"));
        datas.add(new Data(R.mipmap.ic_launcher,"c"));
        datas.add(new Data(R.mipmap.ic_launcher,"d"));
        datas.add(new Data(R.mipmap.ic_launcher,"e"));
        datas.add(new Data(R.mipmap.ic_launcher,"f"));
        datas.add(new Data(R.mipmap.ic_launcher,"g"));
        datas.add(new Data(R.mipmap.ic_launcher,"h"));
        datas.add(new Data(R.mipmap.ic_launcher,"i"));
        datas.add(new Data(R.mipmap.ic_launcher,"j"));
        datas.add(new Data(R.mipmap.ic_launcher,"k"));
        datas.add(new Data(R.mipmap.ic_launcher,"l"));
        datas.add(new Data(R.mipmap.ic_launcher,"m"));
        datas.add(new Data(R.mipmap.ic_launcher,"n"));
        datas.add(new Data(R.mipmap.ic_launcher,"o"));
        datas.add(new Data(R.mipmap.ic_launcher,"p"));
        datas.add(new Data(R.mipmap.ic_launcher,"q"));
        datas.add(new Data(R.mipmap.ic_launcher,"r"));
        datas.add(new Data(R.mipmap.ic_launcher,"s"));
        datas.add(new Data(R.mipmap.ic_launcher,"t"));
        datas.add(new Data(R.mipmap.ic_launcher,"u"));
        datas.add(new Data(R.mipmap.ic_launcher,"v"));









    }
    public List<Data> getDatas(){
        return datas;
    }

    private static DataHelper dataHelper;
    public synchronized static DataHelper getInstance(){
        if(dataHelper==null){
            return new DataHelper();
        }else{
            return dataHelper;
        }

    }

}
