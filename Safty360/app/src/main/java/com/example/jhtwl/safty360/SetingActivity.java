package com.example.jhtwl.safty360;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import CustomView.SettingClickView;
import CustomView.SettingItemView;

public class SetingActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SettingClickView styleView;
    private SettingClickView locationView;
    private String[] items = new String[] { "半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);

        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);

        // 自动更新
        initAutoUpdateView();

        // 设置电话归属地
        initNumberAddressView();

        // 归属地风格提示框
        initNumberAddressStyleView();

        // 归属地提示框显示位置
        initNumberShowLocation();
    }

    // 归属地提示框显示位置
    private void initNumberShowLocation() {
        locationView = (SettingClickView) findViewById(R.id.settingNumberAddressShowLocation);
        locationView.setTitle("归属地提示框显示位置");
        locationView.setDesc("设置归属地提示框的显示位置");
        locationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetingActivity.this, AddressLocationActivity.class));
            }
        });
    }

    // 归属地风格提示框
    private void initNumberAddressStyleView() {
        styleView = (SettingClickView) findViewById(R.id.settingNumberAddressStyle);
        styleView.setTitle("归属地提示框风格");

        int style = sharedPreferences.getInt("address_style", 0);
        styleView.setDesc(items[style]);
        styleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseDiagle();
            }
        });
    }

    private void showChooseDiagle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("归属地提示框风格");

        int style = sharedPreferences.getInt("address_style", 0);
        builder.setSingleChoiceItems(items, style, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 保存选择的信息
                sharedPreferences.edit().putInt("address_style", i).commit();
                dialogInterface.dismiss();

                // 设置信息
                styleView.setDesc(items[i]);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }

    // 设置电话归属地
    private void initNumberAddressView() {
        final SettingItemView numberSettingView = (SettingItemView) findViewById(R.id.settingNumberAddress);
        final boolean numberAddress = sharedPreferences.getBoolean("numberAddress", false);
        if (numberAddress) { // 开启
            numberSettingView.setChecked(true);
        } else {
            numberSettingView.setChecked(false);
        }

        numberSettingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberSettingView.isChecked()) {
                    numberSettingView.setChecked(false);
                    sharedPreferences.edit().putBoolean("numberAddress", false).commit();
                } else {
                    numberSettingView.setChecked(true);
                    sharedPreferences.edit().putBoolean("numberAddress", true).commit();
                }
            }
        });

    }

    // 自动更新
    private void initAutoUpdateView() {

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
