package com.game.siwasu17.life_timer2;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Handler clockUpdateHandler;
    private int UPDATE_MSEC = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //設定用FABを押した時の動作
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Preferenceを開く
                Intent intent = new Intent();
                //渡されてくるViewのContext取るのに注意
                intent.setClass(v.getContext(), LifeSettingPrefActivity.class);
                startActivity(intent);
            }
        });

        clockUpdateHandler = new Handler();
        //UIスレッド外から画面操作するためのハンドラ
        //時間を更新するため
        clockUpdateHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                update();
                clockUpdateHandler.postDelayed(this, UPDATE_MSEC);
            }
        }, UPDATE_MSEC);

        //初回の表示
        update();

    }

    private void update(){
        //設定から残り時間を算出、、表示に使う情報にパースして取得
        LifeTimeManager lifeTimeManager = LifeTimeManager.getInstance(this);
        LifeTimeManager.RemainingTimeSet remainingTimeSet = lifeTimeManager.getRemainingTime();

        //表示
        TextView endAgeView = (TextView) findViewById(R.id.end_age);
        TextView yearView = (TextView) findViewById(R.id.remaining_year);
        TextView dayView = (TextView) findViewById(R.id.remaining_day);
        TextView secView = (TextView) findViewById(R.id.remaining_sec);

        endAgeView.setText(String.valueOf(remainingTimeSet.endAge));
        yearView.setText(String.valueOf(remainingTimeSet.year));
        dayView.setText(String.valueOf(remainingTimeSet.day));
        secView.setText(String.valueOf(remainingTimeSet.sec));

    }
}
