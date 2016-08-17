package com.example.jhtwl.safty360;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdvancedToolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_tool);
    }

    public void clickQuaryNumber(View v) {
        startActivity(new Intent(this, QuaryNumberAddressActivity.class));
    }
}
