package com.game.siwasu17.life_timer;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
    private final String CURRENT_TIMEZONE = "Asia/Tokyo";
    //何歳まで生きる？
    private int end_age = 100;
    //生年月日
    private int birth_year = 2000;
    private int birth_month = 12;
    private int birth_day = 25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Java8の日時系を使うためのBackportライブラリを使う
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);

        clockUpdateHandler = new Handler();
        //UIスレッド外から画面操作するためのハンドラ
        //時間を更新するため
        clockUpdateHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView end_age_view = (TextView) findViewById(R.id.end_age);
                TextView year_view = (TextView) findViewById(R.id.remaining_year);
                TextView day_view = (TextView) findViewById(R.id.remaining_day);
                TextView sec_view = (TextView) findViewById(R.id.remaining_sec);

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
                end_age_view.setText(end_age + "");
                year_view.setText(remaining_year + "");
                day_view.setText(remaining_day + "");
                sec_view.setText(remaining_sec + "");

                clockUpdateHandler.postDelayed(this, UPDATE_MSEC);
            }
        }, UPDATE_MSEC);

    }
}
