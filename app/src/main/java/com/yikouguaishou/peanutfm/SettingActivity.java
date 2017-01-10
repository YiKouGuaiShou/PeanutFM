package com.yikouguaishou.peanutfm;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.yikouguaishou.peanutfm.utils.CachData;

public class SettingActivity extends AppCompatActivity {

    private TextView tv_huancun;
    private Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        sw = (Switch) findViewById(R.id.sw_close);


        Log.e("TAG", "onCreate: "+CachData.getTotalSize(this));

        tv_huancun = (TextView) findViewById(R.id.tv_huancun);


        tv_huancun.setText("清理缓存                                        "+CachData.getTotalSize(this));


    }

    public void goPC(View view) {
        startActivity(new Intent(this,PersonInfoActivity.class));
    }

    public void goBack(View view) {
        finish();
    }



    //定时退出，由LineanerLayout触发
    public void setTimeClose(View view) {
        
        if (sw.isChecked())
        {
            sw.setChecked(false);
        }
        else {
            sw.setChecked(true);
        }

        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, final int i, final int i1) {
                Log.e("TAG", "onTimeSet: "+i+"h:"+i1+"min");

                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep((long)(i*60+i1)*3600000);

                                    System.exit(0);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).start();
            }
        }, 0, 0, true);

        timePickerDialog.show();


    }

    //定时退出，由Switch触发
    public void SwitchSetTimeClose(View view) {
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, final int i, final int i1) {
                Log.e("TAG", "onTimeSet: "+i+"h:"+i1+"min");

                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep((long)(i*60+i1)*3600000);

                                    System.exit(0);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).start();
            }
        }, 0, 0, true);

        timePickerDialog.show();
    }

    public void clearCach(View view) {
        CachData.clearAllCache(this);
        tv_huancun.setText("清理缓存                                        "+CachData.getTotalSize(this));
    }

    public void changePass(View view) {
        startActivity(new Intent(this,ChangePasswordActivity.class));
    }
}
