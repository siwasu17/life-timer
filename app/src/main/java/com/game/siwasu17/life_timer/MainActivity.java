package com.game.siwasu17.life_timer;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView clockTextView;
    private Handler clockUpdateHandler;
    private int counter = 0;
    private int UPDATE_MSEC = 1000;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockUpdateHandler = new Handler();
        //UIスレッド外から画面操作するためのハンドラ
        //時間を更新するため
        clockUpdateHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                counter++;
                TextView clock = (TextView) findViewById(R.id.clock);
                clock.setText(counter+"");
                //clockTextView.setText(counter);
                clockUpdateHandler.postDelayed(this,UPDATE_MSEC);
            }
        },UPDATE_MSEC);

    }
}
