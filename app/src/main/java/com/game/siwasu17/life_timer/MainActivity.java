package com.game.siwasu17.life_timer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import org.threeten.bp.Duration;


public class MainActivity extends ActionBarActivity {

    private Handler clockUpdateHandler;
    private int UPDATE_MSEC = 1000;
    //タイムゾーン決め打ち
    //TODO: あとで自動取得にしたい
    private final static String CURRENT_TIMEZONE = "Asia/Tokyo";
    //何歳まで生きる？
    private int end_age;
    //生年月日
    private int birth_year;
    private int birth_month;
    private int birth_day;

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

        //Java8の日時系を使うためのBackportライブラリを使う
        AndroidThreeTen.init(this);
        clockUpdateHandler = new Handler();
        //UIスレッド外から画面操作するためのハンドラ
        //時間を更新するため
        clockUpdateHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLifeTime();
                updateDisplay();
                clockUpdateHandler.postDelayed(this, UPDATE_MSEC);
            }
        }, UPDATE_MSEC);

    }

    //設定を読み込む
    private void updateLifeTime(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        end_age = pref.getInt("key_end_age",100);
        birth_year = pref.getInt("key_birth_year",2000);
        birth_month = pref.getInt("key_birth_month",1);
        birth_day = pref.getInt("key_birth_day",1);
    }

    private void updateDisplay(){
        TextView endAgeView = (TextView) findViewById(R.id.end_age);
        TextView yearView = (TextView) findViewById(R.id.remaining_year);
        TextView dayView = (TextView) findViewById(R.id.remaining_day);
        TextView secView = (TextView) findViewById(R.id.remaining_sec);

        //タイムゾーン
        ZoneId zone = ZoneId.of(CURRENT_TIMEZONE);
        //現在日時
        ZonedDateTime nowDt = Instant.now().atZone(zone);
        //最後の日
        ZonedDateTime lifeEndDt = ZonedDateTime.of(birth_year + end_age, birth_month, birth_day, 0, 0, 0, 0, zone);
        //いまから最後の日までの期間
        Duration duration = Duration.between(nowDt, lifeEndDt);
        //残りの秒数
        long lifeTimeSec = duration.getSeconds();
        long year_long_sec = (365 * 24 * 60 * 60);
        long day_long_sec = (24 * 60 * 60);
        long remaining_year = lifeTimeSec / year_long_sec;
        long remaining_day = (lifeTimeSec - (remaining_year * year_long_sec)) / day_long_sec;
        long remaining_sec = (lifeTimeSec - (remaining_year * year_long_sec) - (remaining_day * day_long_sec));
        //表示
        endAgeView.setText(String.valueOf(end_age));
        yearView.setText(String.valueOf(remaining_year));
        dayView.setText(String.valueOf(remaining_day));
        secView.setText(String.valueOf(remaining_sec));
    }
}
