package com.zhaocius.nettytest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button mSendTextBtn;
    private EditText mSendTextEt;
    private TextView mReceiveTv;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSendTextBtn=findViewById(R.id.btn_send_text);
        mSendTextEt=findViewById(R.id.et_send_text);
        mReceiveTv=findViewById(R.id.tv_receive_text);

        new Thread(new UdpServer()).start();


        mSendTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String context="来自udp通信";
                new Thread(new UdpClient(context)).start();
            }
        });
    }

}


