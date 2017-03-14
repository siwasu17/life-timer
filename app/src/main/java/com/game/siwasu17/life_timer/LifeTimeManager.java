package com.game.siwasu17.life_timer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.threeten.bp.Duration;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

public class LifeTimeManager {
    //タイムゾーン決め打ち
    final static String CURRENT_TIMEZONE = "Asia/Tokyo";
    //何歳まで生きる？
    public static int endAge;
    //生年月日
    public static int birthYear;
    public static int birthMonth;
    public static int birthDay;

    //設定を読み込むn
    public static void updateLifeTime(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        endAge = pref.getInt("key_end_age",100);
        birthYear = pref.getInt("key_birth_year",2000);
        birthMonth = pref.getInt("key_birth_month",1);
        birthDay = pref.getInt("key_birth_day",1);
    }

    public static long getLifeTimeSec(){
        //タイムゾーン
        ZoneId zone = ZoneId.of(CURRENT_TIMEZONE);
        //現在日時
        ZonedDateTime nowDt = Instant.now().atZone(zone);
        //最後の日
        ZonedDateTime lifeEndDt = ZonedDateTime.of(birthYear + endAge, birthMonth, birthDay, 0, 0, 0, 0, zone);
        //いまから最後の日までの期間
        Duration duration = Duration.between(nowDt, lifeEndDt);
        //残りの秒数
        return duration.getSeconds();
    }
}
