package com.game.siwasu17.life_timer;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by yasu on 2016/12/26.
 */
public class LifeSettingPrefActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FragmentとActivityでワンセット
        getFragmentManager().beginTransaction().replace(android.R.id.content,new LifeSettingPrefFragment()).commit();
    }
}
