package com.jhtwl.googlestore.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jhtwl.googlestore.R;

public class KindOfActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kind_of);
        Button launcherActivityButton = (Button) findViewById(R.id.launcherActivity);
        launcherActivityButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.launcherActivity:
                startActivity(new Intent(this, Launcher_Activity.class));
                break;
        }
    }
}
