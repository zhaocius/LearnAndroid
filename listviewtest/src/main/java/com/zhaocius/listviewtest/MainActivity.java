package com.zhaocius.listviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;

public class MainActivity extends Activity {
    private MyListView listView;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listview);
        final MyAdapter myAdapter=new MyAdapter(DataHelper.getInstance().getDatas(),this);
        listView.setAdapter(myAdapter);
        mButton=findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.addView();
                listView.smoothScrollToPosition(myAdapter.getCount());
            }
        });

        listView.setVerticalFadingEdgeEnabled(true);
        listView.setFadingEdgeLength(599);
    }

}
