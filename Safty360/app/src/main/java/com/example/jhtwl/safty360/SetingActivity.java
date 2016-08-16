package com.example.jhtwl.safty360;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import CustomView.SettingItemView;

public class SetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);

        final SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        final SettingItemView settingItemView = (SettingItemView) findViewById(R.id.settingView);
        boolean updateState = sharedPreferences.getBoolean("auto_update", true);

        if (updateState) {
            // 自动更新已开启
            settingItemView.setChecked(true);
        } else {
            settingItemView.setChecked(false);
        }

        settingItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (settingItemView.isChecked()) {
                    settingItemView.setChecked(false);
                    sharedPreferences.edit().putBoolean("auto_update", false).commit();
                } else {
                    settingItemView.setChecked(true);
                    sharedPreferences.edit().putBoolean("auto_update", true).commit();
                }
            }
        });
    }
}
