package com.zhaocius.viewgrouptest.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaozi.viewgrouptest.R;
import com.zhaozi.viewgrouptest.data.Data;
import com.zhaozi.viewgrouptest.data.DataManager;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by zhaozi on 18-1-18.
 */

public class MyFrameLayout extends FrameLayout {
    private static ArrayList<Data> datas;
    private LayoutInflater mInflater;
    private Context mContext;
    public void setContext(Context context){
        this.mContext=context;

    }
    public MyFrameLayout( Context context) {
        super(context);
        Log.d("zztest","one para");

        mContext=context;
        mInflater=LayoutInflater.from(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
        Log.d("zztest","two para");

        mContext=context;
        mInflater=LayoutInflater.from(context);

    }
    public void setAdapter(){
        datas= DataManager.getInstance().getDatas();
        bindViews();
    }
    private void bindViews(){
        removeAllViews();
        addLayout();
    }
    private void addLayout(){
         final int j=0;
        for(int i=0;i<datas.size();i++){
           final MyItem myItem=(MyItem)mInflater.inflate(R.layout.layout,null);
           ImageView imageView=myItem.findViewById(R.id.imageview);
           TextView textView=myItem.findViewById(R.id.textview);
           myItem.setId((datas.get(i).getId()));
            imageView.setBackground(getResources().getDrawable(datas.get(i).getImageSource()));
            textView.setText(datas.get(i).getText());
            myItem.setVisibility(datas.get(i).isShown()?View.VISIBLE:View.GONE);
            myItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<datas.size();i++){
                        if(myItem.getId()==i){
                            Toast.makeText(mContext,"aa"+i,Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });
            int left=i*(200+10);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(left,0,0,50);
            addView(myItem,params);

        }
    }
}
