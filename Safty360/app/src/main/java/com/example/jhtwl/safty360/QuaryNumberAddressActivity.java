package com.example.jhtwl.safty360;

import android.location.Address;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import Utils.AddressDao;

public class QuaryNumberAddressActivity extends AppCompatActivity {

    private EditText tv_number;
    private TextView tv_quaryResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quary_number_address);

        tv_number = (EditText) findViewById(R.id.et_number);
        tv_quaryResult = (TextView) findViewById(R.id.tv_quaryResult);

        // 监听改变
        tv_number.addTextChangedListener(new TextWatcher() {

            // 改变之前的回调
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 正在改变的回调
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 改变之后的回调
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void clickQuary(View v) {
        String numner = tv_number.getText().toString();

        if (TextUtils.isEmpty(numner)) { // 空

            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            tv_number.startAnimation(shake);
            vibrate();
            return;
        }


        // 查询
//        String address = AddressDao.getAddress(numner);
//        tv_quaryResult.setText(address);
    }

    // 手机震动, 需要权限 android.permission.VIBRATE
    private void vibrate() {
        Vibrator vibrator  = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(new long[] {1000, 2000, 1000, 3000}, -1);
    }
}
