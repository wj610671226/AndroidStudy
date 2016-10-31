package com.jhtwl.googlestore.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.jhtwl.googlestore.R;
import com.jhtwl.googlestore.Services.MyService;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        Button startBtn = (Button) findViewById(R.id.startServices);
        Button stopBtn = (Button) findViewById(R.id.stopServices);
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startServices:
                startService(new Intent(this, MyService.class));
                break;
            case R.id.stopServices:
                stopService(new Intent(this, MyService.class));
                break;
        }
    }
}
