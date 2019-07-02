package com.example.databindingtest;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.databindingtest.databinding.ActivityMain1Binding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain1Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_main1);
        UserBean userBean = new UserBean ("张三", 25);
        binding.setUser(userBean );
    }
}
