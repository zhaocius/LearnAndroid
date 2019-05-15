package com.zhaocius.viewgrouptest;

import android.app.Activity;
import android.os.Bundle;

import com.zhaocius.viewgrouptest.view.MyFrameLayout;

public class MainActivity extends Activity {
    private MyFrameLayout myFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myFrameLayout=findViewById(R.id.myframelayout);
        myFrameLayout.setContext(this);
        myFrameLayout.setAdapter();

    }
}
