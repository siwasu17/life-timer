package com.game.siwasu17.life_timer;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * 設定をxmlと連動して保存するパーツ
 */
public class LifeSettingPrefFragment extends PreferenceFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //res/xmlファイルと連動
        addPreferencesFromResource(R.xml.life_setting_pref);
    }
}
