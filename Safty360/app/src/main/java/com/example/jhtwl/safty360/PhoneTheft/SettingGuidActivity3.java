package com.example.jhtwl.safty360.PhoneTheft;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.jhtwl.safty360.R;

public class SettingGuidActivity3 extends BaseSettingGuidActivity {

    private EditText saftyPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_guid3);

        saftyPhone = (EditText) findViewById(R.id.et_saftyPhone);
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this, SettingGuidActivity4.class));
        finish();
        // 连个界面之间的切换动画
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    @Override
    public void showLastPage() {
        startActivity(new Intent(this, SettingGuidActivity2.class));
        finish();
        // 连个界面之间的切换动画
        overridePendingTransition(R.anim.tran_last_in, R.anim.tran_last_out);
    }

    // 选择联系人
    public void choseContact(View v) {
        startActivityForResult(new Intent(this, ContactActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            String phone = data.getStringExtra("phone");
            phone = phone.replaceAll("-", "").replaceAll(" ", "");// 替换-和空格
            saftyPhone.setText(phone);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
