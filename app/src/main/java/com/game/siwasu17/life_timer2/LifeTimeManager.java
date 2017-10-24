package com.game.siwasu17.life_timer2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Duration;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

public class LifeTimeManager {

    private static LifeTimeManager singleton;
    private Context context;

    //タイムゾーン決め打ち
    static final String CURRENT_TIMEZONE = "Asia/Tokyo";

    //何歳まで生きる？
    public int endAge;
    //生年月日
    public int birthYear;
    public int birthMonth;
    public int birthDay;

    private LifeTimeManager() {
        singleton = null;
    }

    public static LifeTimeManager getInstance(Context context) {
        if (singleton == null) {
            singleton = new LifeTimeManager();
        }
        singleton.context = context;
        //取り出すときに毎回設定をreload
        singleton.updateLifeTime();
        return singleton;
    }

    /**
     * Preferenceから目標年齢と生年月日設定を取得
     * 同時に残り時間も算出する
     */
    public void updateLifeTime() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.context);
        endAge = pref.getInt("key_end_age", 100);
        birthYear = pref.getInt("key_birth_year", 2000);
        birthMonth = pref.getInt("key_birth_month", 1);
        birthDay = pref.getInt("key_birth_day", 1);
    }

    /**
     * 残りの寿命を秒数で取得
     */
    public long getLifeTimeSec() {
        //Java8の日時系を使うためのBackportライブラリを使う
        AndroidThreeTen.init(context);
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

    public RemainingTimeSet getRemainingTime() {
        return new RemainingTimeSet(endAge, getLifeTimeSec());
    }


    /**
     * 残り時間を解釈して表現するクラス
     */
    class RemainingTimeSet {

        static final long YEAR_LONG_SEC = (365 * 24 * 60 * 60);
        static final long DAY_LONG_SEC = (24 * 60 * 60);

        int endAge;
        long year;
        long day;
        long sec;

        public RemainingTimeSet(int endAge, long lifeTimeSec) {
            this.endAge = endAge;
            this.year = lifeTimeSec / YEAR_LONG_SEC;
            this.day = (lifeTimeSec - (this.year * YEAR_LONG_SEC)) / DAY_LONG_SEC;
            this.sec = lifeTimeSec - (this.year * YEAR_LONG_SEC) - (this.day * DAY_LONG_SEC);
        }
    }
}
