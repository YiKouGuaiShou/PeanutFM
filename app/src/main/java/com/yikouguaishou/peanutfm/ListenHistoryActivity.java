package com.yikouguaishou.peanutfm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ListenHistoryActivity extends AppCompatActivity {

    private ListView lv_history;
    private TextView tv_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_history);

        lv_history = (ListView) findViewById(R.id.lv_listenhistory);
        tv_history = (TextView) findViewById(R.id.tv_history);
    }

    public void clearHistory(View view) {
        tv_history.setVisibility(View.VISIBLE);
        lv_history.setVisibility(View.INVISIBLE);
    }
}
