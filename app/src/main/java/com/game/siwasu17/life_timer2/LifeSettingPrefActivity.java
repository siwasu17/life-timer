package com.game.siwasu17.life_timer2;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * 設定画面用Activity
 */
public class LifeSettingPrefActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FragmentとActivityでワンセット
        getFragmentManager().beginTransaction().replace(android.R.id.content,new LifeSettingPrefFragment()).commit();
    }
}
