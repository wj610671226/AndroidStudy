package com.jhtwl.googlestore.Activity;

import android.content.res.Configuration;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.jhtwl.googlestore.R;

import java.util.List;

public class PreferenceTestActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 为界面设置一个标题
        if (hasHeaders()) {
            Button button = new Button(this);
            button.setText("设置操作");
            // 将该按钮添加到该界面上
            setListFooter(button);
        }
    }

    // 重写该方法，负责加载界面布局文件
    @Override
    public void onBuildHeaders(List<Header> target) {
        // 加载选项设置列表的布局文件
        loadHeadersFromResource(R.xml.preference_headers, target);

    }

    // 重写该方法，验证各PreferenceFragment是否有效
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    public static class Prefs1Fragment extends PreferenceFragment {

        private EditTextPreference editTextPreference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            editTextPreference = (EditTextPreference) findPreference("name");

            // 设置点击监听
            editTextPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Log.e("onPreferenceClick", preference.getKey());
                    return true;
                }
            });

            // 设置内容改变的监听
            editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Log.e("onPreferenceChange", newValue.toString());
                    editTextPreference.setSummary(newValue.toString());
                    return true;
                }
            });
        }
    }

    public static class Prefs2Fragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.display_prefs);
            // 获取传入该Fragment的参数
            String website = getArguments().getString("website");
            Toast.makeText(getActivity(), "网站域名是：" + website, Toast.LENGTH_SHORT).show();;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e("newConfig", newConfig.toString());
    }
}
