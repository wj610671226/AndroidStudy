package com.example.jhtwl.safty360.PhoneTheft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jhtwl.safty360.R;

public class PhoneTheftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_theft);

        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        boolean settingGuide = sharedPreferences.getBoolean("settingGuide", false);
        if (!settingGuide) { // 第一次使用
            startActivity(new Intent(this, SettingGuidActivity1.class));
            finish();
            return;
        }
    }

    public void clickAgainSetingGuide(View v) {
        startActivity(new Intent(this, SettingGuidActivity1.class));
        finish();
    }
}
