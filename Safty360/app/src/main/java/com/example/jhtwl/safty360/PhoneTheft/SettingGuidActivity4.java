package com.example.jhtwl.safty360.PhoneTheft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jhtwl.safty360.R;

public class SettingGuidActivity4 extends BaseSettingGuidActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_guid4);
    }

    @Override
    public void showLastPage() {
        startActivity(new Intent(this, SettingGuidActivity3.class));
        finish();
        // 连个界面之间的切换动画
        overridePendingTransition(R.anim.tran_last_in, R.anim.tran_last_out);
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this, PhoneTheftActivity.class));
        finish();
        // 连个界面之间的切换动画
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);

        // 已经设置
        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("settingGuide", true).commit();
    }
}
