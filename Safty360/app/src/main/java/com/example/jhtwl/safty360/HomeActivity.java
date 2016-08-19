package com.example.jhtwl.safty360;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhtwl.safty360.PhoneTheft.PhoneTheftActivity;

import Utils.MD5Utils;

public class HomeActivity extends AppCompatActivity {

    private String[] titleArray = new String[] {"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计",
            "手机杀毒", "缓存清理", "高级工具", "设置中心"};
    private int[] imageArray = new int[] {R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable.home_apps, R.drawable.home_taskmanager, R.drawable.home_netmanager,
            R.drawable.home_trojan, R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings};

    private GridView gridViewHome;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);

        gridViewHome = (GridView) findViewById(R.id.gv_home);
        gridViewHome.setAdapter(new MyAdapter());
        gridViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: // 手机防
                        checkIsFirstLogin();
                        break;
                    case 1: // 通讯卫士
                        startActivity(new Intent(HomeActivity.this, CommunicationGuardActivity.class));
                        break;
                    case 7: // 高级工具
                        startActivity(new Intent(HomeActivity.this, AdvancedToolActivity.class));
                        break;
                    case 8: // 设置中心
                        startActivity(new Intent(HomeActivity.this, SetingActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    // 检查是否是首次使用
    private void checkIsFirstLogin() {
        String password = sharedPreferences.getString("passWord", null);
        if (TextUtils.isEmpty(password)) {
            // 首次使用
            showPassWordDiaglog();
            return;
        }

        // 显示密码输入框
        showPassWordDiaglogInput();
    }

    // 显示密码输入框
    private void showPassWordDiaglogInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        final View view = View.inflate(HomeActivity.this, R.layout.password_input, null);
        dialog.setView(view);
        Button sureButton = (Button) view.findViewById(R.id.sureBtn);
        final Button cancalButton = (Button) view.findViewById(R.id.cancelBtn);
        final EditText passWordTextView = (EditText) view.findViewById(R.id.password);
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = passWordTextView.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(HomeActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String savePassWord = sharedPreferences.getString("passWord", null);
                if (savePassWord.equals(MD5Utils.md5Encode(password))) {
                    dialog.dismiss();
                    startActivity(new Intent(HomeActivity.this, PhoneTheftActivity.class));
                } else {
                    Toast.makeText(HomeActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }

        });
        dialog.show();
    }

    // 显示设置密码框
    private void showPassWordDiaglog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(HomeActivity.this, R.layout.seting_set_password, null);
        dialog.setView(view);
        Button sureButton = (Button) view.findViewById(R.id.sureBtn);
        Button cancalButton = (Button) view.findViewById(R.id.cancelBtn);
        final EditText passWordTextView = (EditText) view.findViewById(R.id.password);
        final EditText againPassWordTextView = (EditText) view.findViewById(R.id.againPassword);

        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passWord = passWordTextView.getText().toString();
                String againPassWord = againPassWordTextView.getText().toString();
                if (!TextUtils.isEmpty(passWord) && !TextUtils.isEmpty(againPassWord)) {
                    if (passWord.equals(againPassWord)) {
                        // 保存密码
                        sharedPreferences.edit().putString("passWord", MD5Utils.md5Encode(passWord)).commit();
                        dialog.dismiss();
                        // 进入下一个页面
                        startActivity(new Intent(HomeActivity.this, PhoneTheftActivity.class));
                    } else {
                        Toast.makeText(HomeActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }

        });
        dialog.show();
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return titleArray.length;
        }

        @Override
        public Object getItem(int i) {
            return titleArray[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View homeListItem = View.inflate(HomeActivity.this, R.layout.home_list_item, null);
            ImageView imageView = (ImageView) homeListItem.findViewById(R.id.home_item_iv_item);
            TextView textView = (TextView) homeListItem.findViewById(R.id.home_item_tv_item);
            imageView.setImageResource(imageArray[i]);
            textView.setText(titleArray[i]);
            return homeListItem;
        }
    }
}
