package com.zhaocius.listviewtest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by zhaozi on 17-12-12.
 */

public class MyAdapter extends BaseAdapter {
    private List<Data> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    Log.d("zztest1",Thread.currentThread().getName());
                    mDatas.add((Data)msg.obj);
                    notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    } ;
    public MyAdapter(List<Data> datas,Context context){
        this.mDatas=datas;
        this.mContext=context;
        mInflater=LayoutInflater.from(mContext);



    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        Log.d(TAG, "getView: convertView == "+convertView);
        if(convertView==null){

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.items, null);
            holder.imageview=(ImageView)convertView.findViewById(R.id.image);
            holder.textview=(TextView)convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }


        holder.textview.setText(mDatas.get(position).textSource);
        holder.imageview.setImageResource(mDatas.get(position).imageSource);

        return convertView;

    }

    private class ViewHolder{
        ImageView imageview;
        TextView textview;
    }

    public void addView(){

        new Thread(){
            public void run(){
                Log.d("zztest",Thread.currentThread().getName());
                Data data=new Data(R.mipmap.ic_launcher,"zz");
                try{
                    Thread.sleep(3000);
                }catch (Exception e){

                }
                Message msg=new Message();
                msg.what=1;
                msg.obj=data;
                mHandler.sendMessage(msg);
            }
        }.start();
    }
}
