package com.example.jhtwl.safty360.PhoneTheft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhtwl.safty360.R;

public class SettingGuidActivity2 extends BaseSettingGuidActivity {

    private CheckBox box;
    private TextView bindView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_guid2);

        box = (CheckBox) findViewById(R.id.bindBox);
        bindView = (TextView) findViewById(R.id.simState);

        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        String simNumber = sharedPreferences.getString("sim", null);
        if (!TextUtils.isEmpty(simNumber)) {
            bindView.setText("sim卡已经绑定");
        }
    }

    @Override
    public void showNextPage() {
        String simNumber = sharedPreferences.getString("sim", null);
        if (TextUtils.isEmpty(simNumber)) {
            Toast.makeText(this, "请绑定sim卡", Toast.LENGTH_SHORT).show();
//            return;
        }

        startActivity(new Intent(this, SettingGuidActivity3.class));
        finish();
        // 连个界面之间的切换动画
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    @Override
    public void showLastPage() {
        startActivity(new Intent(this, SettingGuidActivity1.class));
        finish();
        // 连个界面之间的切换动画
        overridePendingTransition(R.anim.tran_last_in, R.anim.tran_last_out);
    }

    public void clickCheckBoxParent(View v) {

        if (box.isChecked()) {
            box.setChecked(false);
            bindView.setText("sim卡没有绑定");
            sharedPreferences.edit().remove("sim").commit();
        } else {
            box.setChecked(true);
            bindView.setText("sim卡已经绑定");
            TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            String simSerialNumber = manager.getSimSerialNumber();// 获取sim卡序列号

            sharedPreferences.edit().putString("sim", simSerialNumber).commit();
        }
    }
}
