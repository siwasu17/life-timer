package com.game.siwasu17.life_timer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import org.threeten.bp.Duration;


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

    private void updateDisplay(int endAge, long lifeTimeSec){
        TextView endAgeView = (TextView) findViewById(R.id.end_age);
        TextView yearView = (TextView) findViewById(R.id.remaining_year);
        TextView dayView = (TextView) findViewById(R.id.remaining_day);
        TextView secView = (TextView) findViewById(R.id.remaining_sec);

        long year_long_sec = (365 * 24 * 60 * 60);
        long day_long_sec = (24 * 60 * 60);
        long remaining_year = lifeTimeSec / year_long_sec;
        long remaining_day = (lifeTimeSec - (remaining_year * year_long_sec)) / day_long_sec;
        long remaining_sec = (lifeTimeSec - (remaining_year * year_long_sec) - (remaining_day * day_long_sec));
        //表示
        endAgeView.setText(String.valueOf(endAge));
        yearView.setText(String.valueOf(remaining_year));
        dayView.setText(String.valueOf(remaining_day));
        secView.setText(String.valueOf(remaining_sec));
    }

    private void update(){
        LifeTimeManager lifeTimeManager = LifeTimeManager.getInstance(this);
        lifeTimeManager.updateLifeTime();
        long lifeTimeSec = lifeTimeManager.getLifeTimeSec();
        updateDisplay(lifeTimeManager.endAge,lifeTimeSec);
    }
}
