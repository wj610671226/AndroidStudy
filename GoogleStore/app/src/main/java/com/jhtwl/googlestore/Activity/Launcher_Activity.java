package com.jhtwl.googlestore.Activity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.jhtwl.googlestore.R;

public class Launcher_Activity extends LauncherActivity {

    private String name[] = new String[]{"ExpandableListTestActivity", "PreferenceTestActivity"};
    private Class className[] = new Class[] {ExpandableListTestActivity.class, PreferenceTestActivity .class };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, name);
        setListAdapter(adapter);
    }

    @Override
    protected Intent intentForPosition(int position) {
        return new Intent(this, className[position]);
    }
}
