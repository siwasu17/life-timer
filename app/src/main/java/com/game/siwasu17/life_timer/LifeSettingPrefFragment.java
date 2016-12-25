package com.game.siwasu17.life_timer;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by yasu on 2016/12/26.
 */
public class LifeSettingPrefFragment extends PreferenceFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //res/xmlファイルと連動
        addPreferencesFromResource(R.xml.life_setting_pref);
    }
}
