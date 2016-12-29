package com.yikouguaishou.peanutfm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {

    private Switch switch_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        switch_setting = (Switch) findViewById(R.id.switch_setting);



    }
}
