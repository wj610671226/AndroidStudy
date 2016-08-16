package com.example.jhtwl.safty360.PhoneTheft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jhtwl.safty360.R;

public class SettingGuidActivity1 extends BaseSettingGuidActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_guid1);
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this, SettingGuidActivity2.class));
        finish();
        // 连个界面之间的切换动画
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    @Override
    public void showLastPage() {

    }
}
