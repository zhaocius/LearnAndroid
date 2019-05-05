package com.zhaozi.jsontest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.tv_json)
    TextView mTextView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Member member=new Member("zhaozi","192.168.8.117",2661,false,false,false);
        List<Member> memberList=new ArrayList<Member>();
        memberList.add(member);
        String json= JSON.toJSONString(new Command(10001,"selfInfo",null,1,memberList));
        mTextView.setText(json);
        Log.d(TAG, "onCreate: "+json);
    }
}
