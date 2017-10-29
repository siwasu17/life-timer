package com.game.siwasu17.life_timer2;

import android.app.Application;

import jp.co.yahoo.android.mobileinsight.MobileInsight;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MobileInsight.init(this, "" + R.string.MI_APP_ID, "" + R.string.MI_SECRET_ID);
    }
}
