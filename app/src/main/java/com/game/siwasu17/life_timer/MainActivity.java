package com.game.siwasu17.life_timer;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;


public class MainActivity extends ActionBarActivity {

    private Handler clockUpdateHandler;
    private int UPDATE_MSEC = 1000;
    //何歳まで生きる？
    private int end_age = 100;

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
                // 現在日時
                ZonedDateTime nowZonedDt = Instant.now().atZone(ZoneId.of("Asia/Tokyo"));
                TextView clock = (TextView) findViewById(R.id.clock);
                clock.setText(nowZonedDt.toString());
                //clockTextView.setText(counter);
                clockUpdateHandler.postDelayed(this, UPDATE_MSEC);
            }
        }, UPDATE_MSEC);

    }
}
